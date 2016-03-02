/* 
 * Hangman.java 
 * 
 * Version: 
 *     v1 
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This program is a game being played between multiple users where
 * each user gets a Random word which he is supposed to guess within a maximum of 8 chances.
 * The user with the least Guesses wins!
 * The players play one after the other and each gets a word of the same length
 * @author      Amit Shah
 * 
 */

public class Hangman {
	public static void main(String args[]) throws FileNotFoundException
	{
		Random rand = new Random();
		int n = 0,tempApostrophe=0,arraySize=0, currentPlayer=1,players,length,points=0,i=0,z=0;//temp Variables
		int wrongCounter=0,rightCounter=0,userInputNumber=0,rightGuess=0;//temp Variables
		String word="",Apostrophe="'",_Found,repeated,rightFlag,wrongWord,line;//temp Variables
		Scanner scan=new Scanner(new File("words"));//input file source
		Scanner input=new Scanner(System.in);
		System.out.println("Enter the desired Length of the word (< 7) ");
		length=(input.nextInt());
		System.out.println("Enter the number of users Playing ");
		players=(input.nextInt());
		int playerPoints[]=new int[players+1];
		char s[],ans[],userInput[],users[];//Character arrays used for storing answers and inputs

		while (currentPlayer<=players){// loop to handle n number of players
			n=0;

			while(scan.hasNext()){//Randomly allocate one word per user
				++n;
				line = scan.nextLine();

				if(rand.nextInt(n) == 0){
					if(line.length()==length)
						word=line;
				}
				if(word=="")
					word=line;
			}

			word=word.toUpperCase();
			//word="WANI'S";

			System.out.println("Word Selected: "+word);//Showing the word to make guessing easier for testing purpose
			
			s=word.toCharArray();//array which stores the entire Word split into individual characters
			arraySize=s.length;
			Apostrophe="'";

			for(tempApostrophe=0;tempApostrophe<s.length;tempApostrophe++){
				//if the word contains Apostrophe handle it

				if(s[tempApostrophe]==Apostrophe.charAt(0)){
					//	System.out.println("inside if");
					s[tempApostrophe]=s[tempApostrophe+1];
					//System.out.println("current location"+tempApostrophe);				
					s[tempApostrophe+1]=0x0;
					arraySize=s.length-1;
					//System.out.println("arraySize"+arraySize);
				}

			}
			word = new String(s);
			word =word.trim();
			System.out.println("After Trimming Apostrophe's: "+word);

			/*for(int a=0;a<arraySize;a++){
				System.out.print(" Character "+s[a]);
			}*/

			System.out.println();
			ans=new char[arraySize];

			//create a blank array that displays the user's current correct inputs

			for(int a=0;a<=arraySize-1;a++){
				ans[a]='_';
			}

			System.out.println("Your word is: ");

			for(int a=0;a<=arraySize-1;a++){
				ans[a]='_';
				System.out.print("_ ");
			}


			userInput=new char[100];//create an array that stores All user Inputs.

			System.out.println("\nStart guessing: ");
			String user=(input.next());
			user=user.toUpperCase();

			//resetting temporary variables
			wrongCounter=0;rightCounter=0;userInputNumber=0;
			_Found="y";repeated="n";rightFlag="n";
			wrongWord="n";

			users=user.toCharArray();//to handle a string input

			while(_Found=="y" && wrongCounter<7){
				/* 2 break conditions..  
			1. Either user has guessed it all
			2. He has used all 8 attempts */

				if(users.length>1){//handling if user enters the entire word.
					if(user.equals(word)){
						System.out.println("Congratulations, you guessed the complete word correctly");
						rightGuess++;
						//points=points+20;
						break;
					}
					else{
						wrongWord="y";
						wrongCounter+=2; //Wrong complete word = 2 chances missed
						System.out.print("Sorry you entered the Wrong Word!! ");
						System.out.println("You will lose 2 chances");
						System.out.println("Number of Wrong Attempts"+wrongCounter+"/8");
					}
				}

				for(z=0;z<=99;z++){// Checking and displaying Message if user repeats a character by mistake
					if(userInput[z]!=0x0){
						if(users.length==1){
							if(user.charAt(0)==(userInput[z])){
								System.out.println("You already entered "+user+ " before");
								repeated="y";
							}
						}
					}
				}

				if(users.length==1){//Storing all user character inputs in an array
					userInput[userInputNumber]=user.charAt(0);
					userInputNumber++;
				}

				if(repeated=="n" && wrongWord=="n"){
					// if user enters a new character 
					for(i=0;i<arraySize;i++){	
						if((user.charAt(0))==(s[i])){//check all locations where the input character matches
							ans[i]=s[i];
							rightCounter++;//setting counter for number of right characters
							rightGuess++;//Total right guesses counter
							rightFlag="y";
						}
					}
					
					if(rightFlag=="n"){//if wrong character
						wrongCounter++;//Total wrong guesses Counter
						System.out.println("Sorry you entered the Wrong Character!");
						System.out.println("Number of Wrong Attempts"+wrongCounter+"/8");
						//showing user how many chances he has
					}


					if(rightCounter>0){	//if user guesses correctly
						System.out.println("Correct!");
					}

					_Found="n";
					
					for(i=0;i<arraySize;i++){
						System.out.print(ans[i]+" ");
						if(ans[i]=='_')
							_Found="y";
					} //Setting _Found flag to yes if '_' character is found

				}//end of new character loop

				else _Found="y";

				user=null;
				users=null;
				if (_Found=="y"){//resetting values if there are still characters to be guessed.
					System.out.println("Enter next Character");
					user=input.next();
					user=user.toUpperCase();
					users=user.toCharArray();
					rightFlag="n";
					rightCounter=0;
					repeated="n";
					wrongWord="n";
				}

			}//end of while loop
			
			//display user His Score
			
			System.out.println("\n\n\n**************************************\n GAME OVER! Thankyou\n");
			System.out.println("The Correct Answer is:");
			
				System.out.print(word);
			
			System.out.println("\nYou gave: "+wrongCounter+"/8 wrong Answers");
			playerPoints[currentPlayer]=(8-wrongCounter)*10+((length-rightGuess)*10);
			System.out.println("\nYour Final Score "+playerPoints[currentPlayer]);
			System.out.println("\n**************************************");
			currentPlayer++;
			
			//Resetting Flags and counters for new Users
			word="";line="";
			wrongCounter=0;
			points=0;
			scan=new Scanner(new File("C:/Eclipse/MyFirstAssignment/src/words"));//Refreshing the file
			
		}//end of multiple player's Game
		
		//Final scores
		
		System.out.println("\n\n\n**************************************\n You have completed the game, Thankyou\n");
		System.out.println("The Player Scores are:");
		for(i=1;i<=players;i++){
			System.out.println("Player "+i+": "+playerPoints[i]);
		}
		System.out.println("\n**************************************");

		input.close();
		scan.close();
	}//end of main
}// end of class
