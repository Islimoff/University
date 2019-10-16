package com.foxminded.university;

import static java.lang.System.*;

import java.sql.SQLException;
import java.util.Scanner;

import com.foxminded.university.menu.Menu;
import com.foxminded.university.menu.Options;

public class Main {

	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(in);
		Options options = new Options(scanner);
		Menu menu = new Menu(options);
		String answer;
		do {
			menu.displayMainMenu();
			System.out.println("Continue? y or n");
			answer = scanner.nextLine();
		} while (answer.equals("y"));
		scanner.close();
	}
}