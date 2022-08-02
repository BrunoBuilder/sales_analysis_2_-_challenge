package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] folders = line.split(",");
				list.add(new Sale(Integer.parseInt(folders[0]), 
						Integer.parseInt(folders[1]), 
						folders[2], 
						Integer.parseInt(folders[3]), 
						Double.parseDouble(folders[4])));
			
				line = br.readLine();
			}
			
			Set<String> sellers = list.stream()
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getSeller())
					.collect(Collectors.toSet());
			
			System.out.println();
			System.out.println("Total sales by seller:");
			for (String seller : sellers) {
				double totalSeller = list.stream()
						.filter(x -> x.getSeller().equals(seller))
						.map(x -> x.getTotal())
						.reduce(0.0, (x,y) -> x + y);
				System.out.printf("%s - R$ %.2f%n", seller, totalSeller);
				
			}
			
		} catch(IOException e) {
			System.out.println("Error: " + e.getMessage());	
		}

		sc.close();
	}

}
