

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Train  {
    double maleCount=0,femaleCount=0,yesCount=0,noCount=0,countA=0,countB=0,countC=0,maleYesCount=0,femaleYesCount=0,maleNoCount=0,femaleNoCount=0,AYesCount=0,BYesCount=0,CYesCount=0,ANoCount=0,BNoCount=0,CNoCount=0,totalYesAge=0,meanYesAge,SDYesAge=0,totalNoAge=0,meanNoAge,SDNoAge=0;
    public static void main(String[] args) throws IOException{
        ArrayList <Passenger> passengers= new ArrayList<Passenger>();
        Scanner sc= new Scanner(new File("train.csv"));
        while(sc.hasNext()) {
            String temp=sc.nextLine();
            String[] s=temp.split(",");
            Passenger p=new Passenger(s[1],s[0],s[3],Integer.parseInt(s[2]),Double.parseDouble(s[4]),Integer.parseInt(s[5]));
            passengers.add(p);
        }
        Train tr=new Train();

        Collections.shuffle(passengers,new Random(5));
        int k=10;
        int perFold=passengers.size()/k;
        int start;
        int end=-1;
        double[] accuracy=new double[k];
        double sum=0,averageAccuracy;
        for(int i=0;i<k;i++) {
            start=end+1;
            end=start+perFold;
            if(end>=passengers.size()) {end=passengers.size();}
            tr.train(passengers,start,end);
            accuracy[i]=tr.test(passengers,start,end,i);
            sum+=accuracy[i];
            System.out.println("The accuracy for "+(i+1)+" is "+(accuracy[i]*100)+"%.");
        }
        averageAccuracy=sum/k;
        System.out.println("\nThe average accuracy is "+(averageAccuracy*100)+"%.");
    }

    public void train(ArrayList <Passenger> passengers,int start,int end) {

        int i=0;
        for(;i<passengers.size();i++) {
            if(i<start || i>=end) {
                Passenger pr= passengers.get(i);
                if(pr.getSurvived()==1) {
                    yesCount++;
                    totalYesAge+=pr.getAge();
                    if(pr.getGender().equals("male")) {
                        maleCount++;
                        maleYesCount++;
                        if(pr.getPassengerClass()==1) {
                            countA++;
                            AYesCount++;
                        }
                        else if(pr.getPassengerClass()==2) {
                            countB++;
                            BYesCount++;
                        }
                        else if(pr.getPassengerClass()==3) {
                            countC++;
                            CYesCount++;
                        }
                    }
                    else if(pr.getGender().equals("female")) {
                        femaleCount++;
                        femaleYesCount++;
                        if(pr.getPassengerClass()==1) {
                            countA++;
                            AYesCount++;
                        }
                        else if(pr.getPassengerClass()==2) {
                            countB++;
                            BYesCount++;
                        }
                        else if(pr.getPassengerClass()==3) {
                            countC++;
                            CYesCount++;
                        }
                    }
                }
                else if(pr.getSurvived()==0) {
                    noCount++;
                    totalNoAge+=pr.getAge();
                    if(pr.getGender().equals("male")) {
                        maleCount++;
                        maleNoCount++;
                        if(pr.getPassengerClass()==1) {
                            countA++;
                            ANoCount++;
                        }
                        else if(pr.getPassengerClass()==2) {
                            countB++;
                            BNoCount++;
                        }
                        else if(pr.getPassengerClass()==3) {
                            countC++;
                            CNoCount++;
                        }
                    }
                    else if(pr.getGender().equals("female")) {
                        femaleCount++;
                        femaleNoCount++;
                        if(pr.getPassengerClass()==1) {
                            countA++;
                            ANoCount++;
                        }
                        else if(pr.getPassengerClass()==2) {
                            countB++;
                            BNoCount++;
                        }
                        else if(pr.getPassengerClass()==3) {
                            countC++;
                            CNoCount++;
                        }
                    }
                }
            }}
        meanYesAge=totalYesAge/yesCount;
        meanNoAge=totalNoAge/noCount;
        for(Passenger pr: passengers) {
            if(pr.getSurvived()==1) {SDYesAge+=Math.pow((pr.getAge()-meanYesAge), 2);}
            else if(pr.getSurvived()==0){SDNoAge+=Math.pow((pr.getAge()-meanNoAge), 2);}

        }
        SDYesAge/=(yesCount-1);
        SDYesAge=Math.pow(SDYesAge, 0.5);
        SDNoAge/=(noCount-1);
        SDNoAge=Math.pow(SDNoAge, 0.5);}


    public double test(ArrayList <Passenger> passengers,int start,int end,int fold) {
        double TP=0,FP=0,TN=0,FN=0;
        for(int i=start;i<end;i++) {
            Passenger test=passengers.get(i);
            double PGY=0,PCY=0,PAY=0,PGN=0,PAN=0,PCN=0;

            if(test.getGender().equals("male")) {
                PGY=maleYesCount/maleCount;
                PGN=maleNoCount/maleCount;
            }
            else if(test.getGender().equals("female")) {
                PGY=femaleYesCount/femaleCount;
                PGN=femaleNoCount/femaleCount;
            }
            if(test.getPassengerClass()==1) {
                PCY=AYesCount/countA;
                PCN=ANoCount/countA;
            }
            else if(test.getPassengerClass()==2) {
                PCY=BYesCount/countB;
                PCN=BNoCount/countB;
            }
            else if(test.getPassengerClass()==3) {
                PCY=CYesCount/countC;
                PCN=CNoCount/countC;
            }

            double e;
            PAY=1/(2*3.1416);
            PAY=Math.pow(PAY, 0.5);
            PAY=PAY/SDYesAge;
            e=(test.getAge()-meanYesAge);
            e=e/SDYesAge;
            e=e*e;
            e=-0.5*e;
            e=Math.pow(2.718, e);
            PAY=PAY*e;

            PAN=1/(2*3.1416);
            PAN=Math.pow(PAN, 0.5);
            PAN=PAN/SDNoAge;
            e=(test.getAge()-meanNoAge);
            e=e/SDNoAge;
            e=e*e;
            e=-0.5*e;
            e=Math.pow(2.718, e);
            PAN=PAN*e;

            double PY=PAY*PGY*PCY*(yesCount/(yesCount+noCount));
            double PN=PAN*PGN*PCN*(noCount/(yesCount+noCount));
            if(PY>PN && test.getSurvived()==1) {
                TP++;
            }
            else if(PY<PN && test.getSurvived()==0) {
                TN++;
            }
            else if(PY>PN && test.getSurvived()==0) {
                FP++;
            }
            else if(PY<PN && test.getSurvived()==1) {
                FN++;
            }

        }
        double precision=TP/(TP+FP);
        double recall=TP/(TP+FN);
        double fScore=(2*precision*recall)/(precision+recall);
        return fScore;
    }
}
