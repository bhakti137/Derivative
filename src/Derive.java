import java.util.Scanner;
import java.util.ArrayList;

public class Derive {
    public void question() {
        Scanner console = new Scanner(System.in);
        System.out.println("y = ");
        String equation = console.next();
        int length = equation.length();
        solve(equation, length);
        console.close();
    }
    
    public void solve (String equation, int length) {
        if (equation.contains("+") || equation.contains("-")) {
            chainRule(equation,length);
        }
    }

    public void chainRule(String equation, int length) {
        //split equation into terms with signs
        ///count number of terms
        int numTerms = 1;
        for (int i=0; i<length; i++) {
        	if (equation.substring(i,i+1).equals("+") || equation.substring(i,i+1).equals("-")) {
        		numTerms += 1;
        	}
        }
        ///put terms into array
        String term = "";
        ArrayList<String> termSet = new ArrayList<>();
        for (int j=0; j<length; j++) {
    		if (!equation.substring(j,j+1).equals("+") && !equation.substring(j,j+1).equals("-")) {
    			term += equation.substring(j,j+1);
    		} else if (equation.substring(j,j+1).equals("+") || equation.substring(j,j+1).equals("-")) {
    			termSet.add(term);
    			term = "";
    			term += equation.substring(j,j+1);
    		}
    	}
    	termSet.add(term);
        String [] setOfTerms = new String [numTerms];
        setOfTerms = termSet.toArray(setOfTerms);

        //apply chain rule to each term
        int counter = -1;
        for (int k=0; k<setOfTerms.length; k++) {
            if (setOfTerms[k].contains("^")) {
                counter += 1;
                derExp(setOfTerms[k], setOfTerms, counter);
            } else if (setOfTerms[k].contains("x")) {
                counter += 1;
                derVar(setOfTerms[k], setOfTerms, counter);
            } else {
                counter += 1;
                derConst(setOfTerms[k], setOfTerms, counter);
            }
        }

        //print derivative of equation
        String derivative = "";
        for (int n=0; n<setOfTerms.length; n++) {
            derivative += setOfTerms[n];
        }
        int q = derivative.length();
        if (derivative.substring(q-1).equals("+") || derivative.substring(q-1).equals("-")) {
            derivative = derivative.substring(0,q-1);
        }
        System.out.println("\ndy/dx = \n" + derivative);
    }

    public void derExp (String term, String [] setOfTerms, int counter) {
        String hold = term.replace("x^", ",");
        term = hold;
        String expBase [] = term.split(",");
        String base = expBase[0];
        String exp = expBase[1];
        hold = base;
        int expInt = Integer.parseInt(exp);
        int baseInt = Integer.parseInt(base);
        baseInt = baseInt * expInt;
        expInt = expInt - 1;
        exp = Integer.toString(expInt);
        base = Integer.toString(baseInt);
        if (!exp.equals("1")) {
            term = base + "x^" + exp;
        } else {
            term = base + "x";
        }
        if (hold.contains("+")) {
            term = "+" + term;
        }
        setOfTerms[counter] = term;
    }

    public void derVar (String term, String [] setOfTerms, int counter) {
        if (term.length() == 1) {
            setOfTerms[counter] = "1";
        } else {
            String hold = term.replace("x","");
            term = hold;
            setOfTerms[counter] = term;
        }
    }

    public void derConst (String term, String [] setOfTerms, int counter) {
        setOfTerms[counter] = "";
    }
}