package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;
import com.foxminded.university.service.AudienceService;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.SubjectService;
import com.foxminded.university.service.TeacherService;

public class LessonJdbcDao implements LessonDao {

	private ConnecctionProvider provider;
	private AudienceService audienceService = new AudienceService();;
	private GroupService groupService = new GroupService();
	private SubjectService subjectService = new SubjectService();
	private TeacherService teacherService = new TeacherService();
	private static final String INSERT = "INSERT INTO lessons (audience_id,teacher_id, subject_id, group_id, date, start_lesson, end_lesson) values(?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM lessons WHERE lesson_id=?";
	private static final String FINDBYDATE = "SELECT lesson_id FROM lessons WHERE date=?";
	private static final String FINDBYID = "SELECT lesson_id, audience_id, teacher_id, subject_id, group_id, date, start_lesson, end_lesson FROM lessons WHERE lesson_id=?";
	private static final String FINDBYGROUP = "SELECT group_id, date, start_lesson, end_lesson FROM lessons WHERE group_id=?";
	private static final String FINDBYTEACHER = "SELECT teacher_id, date, start_lesson, end_lesson FROM lessons WHERE teacher_id=?";
	private static final String FINDBYAUDIENCE = "SELECT audience_id, date, start_lesson, end_lesson FROM lessons WHERE audience_id=?";

	public LessonJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public Lesson add(Lesson lesson) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, lesson.getAudience().getId());
			statement.setInt(2, lesson.getTeacher().getId());
			statement.setInt(3, lesson.getSubject().getId());
			statement.setInt(4, lesson.getGroup().getId());
			statement.setDate(5, Date.valueOf(lesson.getDate()));
			statement.setTime(6, Time.valueOf(lesson.getLessonTime().getStart()));
			statement.setTime(7, Time.valueOf(lesson.getLessonTime().getEnd()));
			statement.execute();

			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			lesson.setId(resultSet.getInt("lesson_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lesson;
	}

	@Override
	public void remove(Lesson lesson) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, lesson.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Lesson findById(int id) {
		Lesson lesson = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				lesson = new Lesson();
				lesson.setId(resultSet.getInt("lesson_id"));
				Audience audience = audienceService.findAudienceById(resultSet.getInt("audience_id"));
				lesson.setAudience(audience);
				Teacher teacher = teacherService.findById(resultSet.getInt("teacher_id"));
				lesson.setTeacher(teacher);
				Subject subject = subjectService.findById(resultSet.getInt("subject_id"));
				lesson.setSubject(subject);
				Group group = groupService.findById(resultSet.getInt("group_id"));
				lesson.setGroup(group);
				lesson.setDate(resultSet.getDate("date").toLocalDate());
				LessonTime time = mapToLessonTime(resultSet);
				lesson.setLessonTime(time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lesson;
	}

	@Override
	public List<Lesson> findByDate(LocalDate date) {
		List<Lesson> lessons = new ArrayList<>();
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYDATE)) {
			statement.setDate(1, Date.valueOf(date));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Lesson lesson = findById(resultSet.getInt("lesson_id"));
				lessons.add(lesson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lessons;
	}

	@Override
	public boolean findGroup(Group group, LocalDate localDate, LessonTime lessonTime) {
		Group findGroup = null;
		LessonTime time = null;
		LocalDate date = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYGROUP)) {
			statement.setInt(1, group.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				date = resultSet.getDate("date").toLocalDate();
				time = mapToLessonTime(resultSet);
				findGroup = groupService.findById(resultSet.getInt("group_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group.equals(findGroup) && localDate.equals(date) && findTime(time, lessonTime);
	}

	@Override
	public boolean findTeacher(Teacher teacher, LocalDate localDate, LessonTime lessonTime) {
		Teacher findTeacher = null;
		LessonTime time = null;
		LocalDate date = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYTEACHER)) {
			statement.setInt(1, teacher.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				date = resultSet.getDate("date").toLocalDate();
				time = mapToLessonTime(resultSet);
				findTeacher = teacherService.findById(resultSet.getInt("teacher_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher.equals(findTeacher) && localDate.equals(date) && findTime(time, lessonTime);
	}

	@Override
	public boolean findAudience(Audience audience, LocalDate localDate, LessonTime lessonTime) {
		Audience findAudience = null;
		LessonTime time = null;
		LocalDate date = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYAUDIENCE)) {
			statement.setInt(1, audience.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				date = resultSet.getDate("date").toLocalDate();
				time = mapToLessonTime(resultSet);
				findAudience = audienceService.findAudienceById(resultSet.getInt("audience_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audience.equals(findAudience) && localDate.equals(date) && findTime(time, lessonTime);
	}

	@Override
	public boolean findTime(LessonTime lessonTime, LessonTime lessonTime2) {
		Duration durationLesson = Duration.between(lessonTime.getStart(), lessonTime.getEnd());
		Duration perspectiveDurationLesson = Duration.between(lessonTime.getStart(), lessonTime2.getStart());
		return durationLesson.toMinutes() > perspectiveDurationLesson.abs().toMinutes();
	}

	private LessonTime mapToLessonTime(ResultSet resultSet) throws SQLException {
		LessonTime time = new LessonTime();
		time.setStart(resultSet.getTime("start_lesson").toLocalTime());
		time.setEnd(resultSet.getTime("end_lesson").toLocalTime());
		return time;
	}
}