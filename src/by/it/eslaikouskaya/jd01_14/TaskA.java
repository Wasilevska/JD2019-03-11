package by.it.eslaikouskaya.jd01_14;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskA {
	private static String getFileName(Class<?> cl, String name) {
		String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
		String strPackage = cl.getPackage().getName().replace(".", File.separator);
		return src + strPackage + File.separator + name;
	}

	public static void main(String[] args) {
		String filename = getFileName(TaskA.class, "dataTaskA.bin");
		String filetxt = getFileName(TaskA.class, "resultTaskA.txt");

		writeInt(filename);
		List<Integer> list = readListInteger(filename);
		printListInteger(list);
		saveListIntegerToTxt(filetxt, list);
		System.out.println(System.getProperty("user.dir"));
	}

	//Записать в двоичный файл dataTaskA.bin 20 случайных чисел типа Integer.
	private static void writeInt(String filename) {
		try (DataOutputStream dos = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(filename)))
		) {
			for (int i = 0; i < 20; i++) {
				int value = (int)(Math.random() * 101 - 50);
				dos.writeInt(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//прочитать записанный файл в коллекцию ArrayList.
	private static List<Integer> readListInteger(String filename) {
		List<Integer> list = new ArrayList<>();
		try (DataInputStream dis = new DataInputStream(
				new BufferedInputStream(new FileInputStream(filename)))
		) {
			while (dis.available() > 0) list.add(dis.readInt());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	//Вывести в консоль прочитанные числа через пробел
	//Вывести с новой строки их среднее арифметическое avg=20.123.
	private static void printListInteger(List<Integer> list) {
		double sum = 0;
		for (Integer integer : list) {
			System.out.print(integer + " ");
			sum += integer;
		}
		System.out.println("\navg=" + sum / list.size());
	}

	//Продублировать вывод в консоль в файл resultTaskA.txt
	private static void saveListIntegerToTxt(String filetxt, List<Integer> list) {
		try (PrintWriter out = new PrintWriter(new FileWriter(filetxt))) {
			double sum2 = 0;
			for (Integer integer : list) {
				out.print(integer + " ");
				sum2 += integer;
			}
			out.println("\navg=" + sum2 / list.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
