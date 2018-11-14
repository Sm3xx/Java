package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {

	public static List<String> readFile(String path) {

		try {
			List<String> output = new ArrayList<>();

			File file = new File(path);

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				if (!st.startsWith("##")) {
					output.add(st);
				}
			}

			return output;
		} catch (Exception e) {
			return null;
		}
	}

	public static HashMap<String, String> createKeyValuePair(List<String> content) {
		HashMap<String, String> output = new HashMap<String, String>();

		for (int i=0; i<content.size(); i++) {
			String key = "";
			String value = "";
			String line = content.get(i);
			if (!line.startsWith("##") && line != "") {
				String[] args = line.split(" ");
				key = args[0].replaceAll(":", "").trim();
	
				for (int j = 1; j < args.length; j++) {
					value = value + args[j] + " ";
				}
	
				value = value.trim();
	
				output.put(key, value);
			}
		}
		return output;
	}

}
