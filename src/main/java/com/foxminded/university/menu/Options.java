package com.foxminded.university.menu;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.foxminded.university.University;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.MonthSchedule;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public class Options {

	private University university;
	private Scanner scanner;

	public Options(University university, Scanner scanner) {
		this.university = university;
		this.scanner = scanner;
	}

	public void addTeacher() {
		System.out.println("Enter the name of the teacher you want to add");
		Teacher teacher = new Teacher();
		teacher.setName(scanner.nextLine());
		university.addTeacher(teacher);
		System.out.println("The teacher successfully added");
	}

	public void removeTeacher() {
		System.out.println("Enter the name of the teacher you want to remove");
		String teacherName = scanner.nextLine();
		Teacher teacher = university.findTeacher(teacherName);
		if (teacher == null) {
			System.out.println("This name was not found");
		} else {
			university.removeTeacher(teacher);
			System.out.println("The teacher successfully removed");
		}
	}

	public void addSubjectsToTeacher() {
		System.out.println("Enter the name of the teacher to whom you want to add the subject");
		String teacherName = scanner.nextLine();
		Teacher teacher = university.findTeacher(teacherName);
		if (teacher == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the name of the subject that the teacher will lead");
			Subject subject = university.findSubject(scanner.nextLine());
			if (subject == null) {
				System.out.println("This name was not found");
			} else {
				university.addSubjectToTeacher(teacher, subject);
				System.out.println("The subject successfully added to teacher");
			}
		}
	}

	public void printTeacherDaySchedule() {
		System.out.println("Enter the name of the teacher for whom you want to get the day schedule");
		Teacher teacher = university.findTeacher(scanner.nextLine());
		if (teacher == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the date in format yyyy-MMM-dd");
			String dateText = scanner.nextLine();
			try {
				LocalDate date = LocalDate.parse(dateText);
				DaySchedule daySchedule = university.findTeacherDaySchedule(teacher, date);
				showDaySchedule(daySchedule);
			} catch (DateTimeParseException e) {
				System.out.println("That's not a valid date.");
			}
		}
	}

	public void printTeacherMonthSchedule() {
		System.out.println("Enter the name of the teacher for whom you want to get the month schedule");
		Teacher teacher = university.findTeacher(scanner.nextLine());
		if (teacher == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the year");
			try {
				int year = Integer.parseInt(scanner.nextLine());
				System.out.println("Enter the month");
				try {
					int month = Integer.parseInt(scanner.nextLine());
					MonthSchedule monthSchedule = university.findTeacherMonthSchedule(teacher, year, Month.of(month));
					showMonthSchedule(monthSchedule);
				} catch (DateTimeException e) {
					System.out.println("That's not a valid month.");
				}
			} catch (NumberFormatException e) {
				System.out.println("That's not a valid number!");
			}
		}
	}

	public void addStudent() throws SQLException {
		System.out.println("Enter the name of the student you want to add");
		Student student = new Student();
		student.setName(scanner.nextLine());
		university.addStudent(student);
		System.out.println("The student successfully added");
	}

	public void removeStudent() {
		System.out.println("Enter the name of the student you want to remove");
		Student student = university.findStudent(scanner.nextLine());
		if (student == null) {
			System.out.println("This name was not found");
		} else {
			university.removeStudent(student);
			System.out.println("The student successfully removed");
		}
	}

	public void printStudentDaySchedule() {
		System.out.println("Enter the name of the student for whom you want to get the day schedule");
		Student student = university.findStudent(scanner.nextLine());
		if (student == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the date in format yyyy-MMM-dd");
			String dateText = scanner.nextLine();
			try {
				LocalDate date = LocalDate.parse(dateText);
				DaySchedule daySchedule = university.findStudentDaySchedule(student, date);
				showDaySchedule(daySchedule);
			} catch (DateTimeParseException e) {
				System.out.println("That's not a valid date.");
			}
		}
	}

	public void printStudentMonthSchedule() {
		System.out.println("Enter the name of the student for whom you want to get the month schedule");
		Student student = university.findStudent(scanner.nextLine());
		if (student == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the year");
			try {
				int year = Integer.parseInt(scanner.nextLine());
				System.out.println("Enter the month");
				try {
					int month = Integer.parseInt(scanner.nextLine());
					MonthSchedule monthSchedule = university.findStudentMonthSchedule(student, year, Month.of(month));
					showMonthSchedule(monthSchedule);
				} catch (DateTimeException e) {
					System.out.println("That's not a valid month.");
				}
			} catch (NumberFormatException e) {
				System.out.println("That's not a valid year!");
			}
		}
	}

	public void addGroup() {
		System.out.println("Enter groupName of the group you want to add");
		Group group = new Group();
		group.setGroupName(scanner.nextLine());
		university.addGroup(group);
		System.out.println("The group successfully added");
	}

	public void removeGroup() {
		System.out.println("Enter name of the group you want to remove");
		Group group = university.findGroup(scanner.nextLine());
		if (group == null) {
			System.out.println("This name was not found");
		} else {
			university.removeGroup(group);
			System.out.println("The group successfully removed");
		}
	}

	public void addStudentInGroup() {
		System.out.println("Enter the name of the group to which you want to add the student");
		Group group = university.findGroup(scanner.nextLine());
		if (group == null) {
			System.out.println("This name was not found");
		} else {
			System.out.println("Enter the name of the student you want to add");
			Student student = university.findStudent(scanner.nextLine());
			if (student == null) {
				System.out.println("This name was not found");
			} else {
				student.setGroupId(group.getId());
				university.updateStudent(student);
				System.out.println("The student successfully added in group");
			}
		}
	}

	public void addAudience() {
		System.out.println("Enter number of the audience you want to add");
		try {
			Audience audience = new Audience();
			audience.setNumber(Integer.parseInt(scanner.nextLine()));
			university.addAudience(audience);
			System.out.println("The audience successfully added");
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid number!");
		}
	}

	public void removeAudience() {
		System.out.println("Enter number of the audience you want to remove");
		try {
			Audience audience = university.findAudience(Integer.parseInt(scanner.nextLine()));
			if (audience == null) {
				System.out.println("This number was not found");
			} else {
				university.removeAudience(audience);
				System.out.println("The audience successfully removed");
			}
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid number!");
		}
	}

	public void addSubject() {
		System.out.println("Enter name of the subject you want to add");
		Subject subject = new Subject();
		subject.setName(scanner.nextLine());
		university.addSubject(subject);
		System.out.println("The subject successfully added");
	}

	public void removeSubject() {
		System.out.println("Enter name of the subject you want to remove");
		Subject subject = university.findSubject(scanner.nextLine());
		if (subject == null) {
			System.out.println("This name was not found");
		} else {
			university.removeSubject(subject);
			System.out.println("The subject successfully removed");
		}
	}

	public void addLesson() {
		try {
			Lesson lesson = new Lesson();
			addLocalDateTimeInLesson(lesson);
			addAudienceInLesson(lesson);
			addTeacherInLesson(lesson);
			addSubjectInLesson(lesson);
			addGroupInLesson(lesson);
			university.addLesson(lesson);
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid number!");
		}
	}

	public void removeLesson() {
		System.out.println("Enter lessonId of the lesson you want to remove");
		try {
			Lesson lesson = university.findLesson(Integer.parseInt(scanner.nextLine()));
			if (lesson == null) {
				System.out.println("This Id was not found");
			} else {
				university.removeLesson(lesson);
				System.out.println("The lesson successfully removed");
			}
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid lessonId!");
		}
	}

	public void addAudienceInLesson(Lesson lesson) {
		System.out.println("Enter the number of the audience in which the lesson will take place");
		try {
			Audience audience = university.findAudience(Integer.parseInt(scanner.nextLine()));
			if (audience == null) {
				System.out.println("This number was not found! Try again");
				addAudienceInLesson(lesson);
			} else if (university.findAudienceInLesson(audience, lesson.getDate(), lesson.getLessonTime())) {
				System.out.println("This audience has already been scheduled lesson at this time");
				addLesson();
			} else {
				lesson.setAudience(audience);
				System.out.println("The audience successfully added");
			}
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid number!. Try again");
			addAudienceInLesson(lesson);
		}
	}

	public void addTeacherInLesson(Lesson lesson) {
		System.out.println("Enter the name of the teacher who will lead the lesson");
		Teacher teacher = university.findTeacher(scanner.nextLine());
		if (teacher == null) {
			System.out.println("This name was not found! Try again");
			addTeacherInLesson(lesson);
		} else if (university.findTeacherInLesson(teacher, lesson.getDate(), lesson.getLessonTime())) {
			System.out.println("This teacher has already been scheduled lesson at this time");
			addLesson();
		} else {
			lesson.setTeacher(teacher);
			System.out.println("The teacher successfully added");
		}
	}

	public void addLocalDateTimeInLesson(Lesson lesson) {
		System.out.println("Enter the date in format yyyy-MMM-dd");
		try {
			LocalDate date = LocalDate.parse(scanner.nextLine());
			lesson.setDate(date);
			System.out.println("The date successfully added");
			addLocalTimeInLesson(lesson, date);
		} catch (DateTimeParseException e) {
			System.out.println("That's not a valid date. Try again");
			addLocalDateTimeInLesson(lesson);
		}
	}

	public void addLocalTimeInLesson(Lesson lesson, LocalDate date) {
		System.out.println("Enter the start time of the lesson in format HH:mm");
		LessonTime lessonTime = new LessonTime();
		lessonTime.setStart(LocalTime.parse(scanner.nextLine()));
		System.out.println("Enter the end time of the lesson in format HH:mm");
		lessonTime.setEnd(LocalTime.parse(scanner.nextLine()));
		lesson.setLessonTime(lessonTime);
		System.out.println("The time successfully added");
	}

	public void addSubjectInLesson(Lesson lesson) {
		System.out.println("Enter name of the subject");
		Subject subject = university.findSubject(scanner.nextLine());
		if (subject == null) {
			System.out.println("This name was not found. Try again");
			addSubjectInLesson(lesson);
		} else {
			lesson.setSubject(subject);
			System.out.println("The subject successfully added");
		}
	}

	public void addGroupInLesson(Lesson lesson) {
		System.out.println("Enter id of the group");
		Group group = university.findGroup(scanner.nextLine());
		if (group == null) {
			System.out.println("This name was not found. Try again");
			addGroupInLesson(lesson);
		} else if (university.findGroupInLesson(group, lesson.getDate(), lesson.getLessonTime())) {
			System.out.println("This group has already been scheduled lesson at this time");
			addLesson();
		} else {
			lesson.setGroup(group);
			System.out.println("The group successfully added");
		}
	}

	public int validateNumber(int maxNumber) {
		try {
			int number = Integer.parseInt(scanner.nextLine());
			if (number < 1 || number > maxNumber) {
				System.out.println("That's not a valid number!");
				return 0;
			} else {
				return number;
			}
		} catch (NumberFormatException e) {
			System.out.println("That's not a valid number!");
			return 0;
		}
	}

	private void showDaySchedule(DaySchedule daySchedule) {
		if (daySchedule == null) {
			System.out.println("No lessons for this day");
		} else {
			System.out.println(university.formatDaySchedule(daySchedule));
		}
	}

	private void showMonthSchedule(MonthSchedule monthSchedule) {
		if (monthSchedule == null) {
			System.out.println("No lessons for this month");
		} else {
			System.out.println(university.formatMonthSchedule(monthSchedule));
		}
	}
}