package com.foxminded.university.menu;

import java.sql.SQLException;

public class Menu {

	private Options options;

	public Menu(Options options) {
		this.options = options;
	}

	public void displayMainMenu() throws SQLException {
		System.out.println("Selected category:");
		System.out.println("1.Teacher");
		System.out.println("2.Student");
		System.out.println("3.Group");
		System.out.println("4.Audience");
		System.out.println("5.Subject");
		System.out.println("6.Lesson");
		int maxNumber = 6;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			displayTeacherMenu();
			break;
		case 2:
			displayStudentMenu();
			break;
		case 3:
			displayGroupMenu();
			break;
		case 4:
			displayAudienceMenu();
			break;
		case 5:
			displaySubjectMenu();
			break;
		case 6:
			displayLessonMenu();
			break;
		}
	}

	private void displayTeacherMenu() {
		System.out.println("Selected action:");
		System.out.println("1.Add teacher");
		System.out.println("2.Remove teacher");
		System.out.println("3.Find teacher day schedule");
		System.out.println("4.Find teacher month schedule");
		int maxNumber = 5;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addTeacher();
			break;
		case 2:
			options.removeTeacher();
			break;
		case 3:
			options.printTeacherDaySchedule();
			break;
		case 4:
			options.printTeacherMonthSchedule();
			break;
		}
	}

	private void displayStudentMenu() throws SQLException {
		System.out.println("Selected action:");
		System.out.println("1.Add student");
		System.out.println("2.Remove student");
		System.out.println("3.Find student day schedule");
		System.out.println("4.Find student month schedule");
		int maxNumber = 4;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addStudent();
			break;
		case 2:
			options.removeStudent();
			break;
		case 3:
			options.printStudentDaySchedule();
			break;
		case 4:
			options.printStudentMonthSchedule();
			break;
		}
	}

	private void displayGroupMenu() {
		System.out.println("Selected action:");
		System.out.println("1.Add group");
		System.out.println("2.Remove group");
		System.out.println("3.Add student in group");
		int maxNumber = 3;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addGroup();
			break;
		case 2:
			options.removeGroup();
			break;
		case 3:
			options.addStudentInGroup();
			break;
		}
	}

	private void displayAudienceMenu() {
		System.out.println("Selected action:");
		System.out.println("1.Add audience");
		System.out.println("2.Remove audience");
		int maxNumber = 2;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addAudience();
			break;
		case 2:
			options.removeAudience();
			break;
		}
	}

	private void displaySubjectMenu() {
		System.out.println("Selected action:");
		System.out.println("1.Add subject");
		System.out.println("2.Remove subject");
		int maxNumber = 2;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addSubject();
			break;
		case 2:
			options.removeSubject();
			break;
		}
	}

	private void displayLessonMenu() {
		System.out.println("Selected action:");
		System.out.println("1.Add lesson");
		System.out.println("2.Remove lesson");
		int maxNumber = 2;
		switch (options.validateNumber(maxNumber)) {
		case 1:
			options.addLesson();
			break;
		case 2:
			options.removeLesson();
		}
	}
}