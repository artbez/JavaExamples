package com.mycompany.app;

import java.util.Scanner;

public class App
{

    public static void main( String[] args ) throws Exception
    {
        System.out.println("Please, print your login");
        Scanner sc = new Scanner(System.in);
        String login = sc.nextLine();
        HttpClientTest httpTestClient = new HttpClientTest(login);
        while(true)
        {
            clearConsole();
            printMenu();
            String choice = sc.nextLine().toString();
            if (choice.equals("1")) {
                System.out.println("Type message");
                if (sc.hasNextLine()) {
                    String text = sc.nextLine();
                    httpTestClient.send(text);
                }
            }
            else if (choice.equals("2")) {
                httpTestClient.getAll();
            }
            else if (choice.equals("3")) {
                break;
            }
        }
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public final static void printMenu()
    {
        System.out.println("1. send message");
        System.out.println("2. get chat");
        System.out.println("3. quit");
    }
}

