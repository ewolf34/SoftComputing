import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Ablauf
{
    // Parameter
    static int anz = 10 * 2; // Anzahl Individuen
    static int maxit = 20; // Maximale Anzahl an Itertionen
    static boolean minimierung = true; // Minimierungsproblem

    // Datenstrukturen f�r GA
    static int[][] POP = new int[anz][]; // Population aus anz Individuen
    static int[] FIT = new int[anz]; // Fitness f�r anz Individuen
    
    static int[] FITFIX = new int[anz];
    static int[] FITVAR = new int[anz];
    
    static int[][] POPCHILD = new int[anz][]; // Kinder aus anz Individuen
    static int[] FITCHILD = new int[anz]; // Fitness f�r anz Kinder

    // Variablen f�r GA
    static int iteration; // Iterationszaehler
    static int bestFit; // Beste Fitness
    static int bestFitFix;
    static int bestFitVar;
    static int rank[];

    public static void main(String[] args)
    {
        if (minimierung)
            bestFit = Integer.MAX_VALUE;
        else
            bestFit = Integer.MIN_VALUE;

        Problem.einlesenInstanz("123UnifS.txt");

        Problem.initialisierung();
        
        
        bewertung(POP, FITFIX, FITVAR);
        rank = ranking(FITFIX);
        int [] ranktemp = ranking(FITVAR);
        for(int i = 0; i < rank.length; i++)
        {
            rank[i] += ranktemp[i];
        }

        /*bewertung(POP, FIT);

        for (iteration = 1; iteration <= maxit; iteration++)
        {
            fortpflanzung();
        }*/
    }
    
    public static int[] ranking(int[] fit)
    {
        
        return 
    }
    
    public static void bewertung(int[][] ind, int[] fit)
    {
        int aktFit;
        if (minimierung)
            aktFit = Integer.MAX_VALUE;
        else
            aktFit = Integer.MIN_VALUE;

        for (int i = 0; i < fit.length; i++)
        {
            fit[i] = Problem.fitness(ind[i]);
            System.out.println(i + " " + fit[i]);

            if (minimierung)
            {
                if (fit[i] < bestFit)
                {
                    bestFit = fit[i];
                }
                if (fit[i] < aktFit)
                {
                    aktFit = fit[i];
                }
            } else
            {
                if (fit[i] > bestFit)
                {
                    bestFit = fit[i];
                }
                if (fit[i] > aktFit)
                {
                    aktFit = fit[i];
                }
            }
        }
        System.out.println(iteration + " " + aktFit + " " + bestFit);

    }

    public static void bewertung(int[][] ind, int[] fitfix, int[] fitvar)
    {
        int aktFitFix;
        int aktFitVar;
        if (minimierung)
        {
            aktFitFix = Integer.MAX_VALUE;
            aktFitVar = Integer.MAX_VALUE;
        }
        else
        {
            aktFitFix = Integer.MIN_VALUE;
            aktFitVar = Integer.MIN_VALUE;
        }
        
        for (int i = 0; i < fitfix.length; i++)
        {
            fitfix[i] = Problem.fitnessFix(ind[i]);
            fitvar[i] = Problem.fitnessVar(ind[i]);

            if (minimierung)
            {
                if (fitfix[i] < bestFitFix)
                {
                    bestFitFix = fitfix[i];
                }
                if (fitfix[i] < aktFitFix)
                {
                    aktFitFix = fitfix[i];
                }
                
                if (fitvar[i] < bestFitVar)
                {
                    bestFitVar = fitvar[i];
                }
                if (fitvar[i] < aktFitVar)
                {
                    aktFitVar = fitvar[i];
                }
            } else
            {
                if (fitfix[i] > bestFitFix)
                {
                    bestFitFix = fitfix[i];
                }
                if (fitfix[i] > aktFitFix)
                {
                    aktFitFix = fitfix[i];
                }
                
                if (fitvar[i] > bestFitVar)
                {
                    bestFitVar = fitvar[i];
                }
                if (fitvar[i] > aktFitVar)
                {
                    aktFitVar = fitvar[i];
                }
            }
        }
    }
    
    public static void ausgabePop()
    {
        System.out.println("Eltern:");
        for (int indi = 0; indi < POP.length; indi++)
        {
            System.out.print(indi + " ");
            for (int st = 0; st < POP[indi].length; st++)
            {
                System.out.print(POP[indi][st]);
            }
            System.out.print(" " + FIT[indi]);
            System.out.println();
        }
    }

    public static void ausgabeChild()
    {

        System.out.println("Childs:");
        for (int indi = 0; indi < POPCHILD.length; indi++)
        {
            System.out.print(indi + " ");
            for (int st = 0; st < POPCHILD[indi].length; st++)
            {
                System.out.print(POPCHILD[indi][st]);
            }
            System.out.print(" " + FITCHILD[indi]);
            System.out.println();
        }
    }

    public static void ersetzteMitWettkampf()
    {
        Map<Integer, Integer> alle = new HashMap<>();
        for (int i = 0; i < anz; i++)
        {
            alle.put(i, FIT[i]);
        }
        for (int i = 0; i < FITCHILD.length; i++)
        {
            alle.put(i + FIT.length, FITCHILD[i]);
        }
        int size = alle.size();
        int x;
        int y;

        for (int i = 0; i < anz; i++)
        {
            do
            {
                x = (int) (size * Math.random());
            } while (alle.containsKey(x));

            do
            {
                y = (int) (size * Math.random());
            } while (alle.containsKey(y) || y == x);

            if (alle.get(x) <= alle.get(y))
            {
                alle.remove(y);
            } else
            {
                alle.remove(x);
            }

        }
        int i = 0;
        for (Entry<Integer, Integer> e : alle.entrySet())
        {
            if (e.getKey() < anz)
            {
                POP[i] = POP[e.getKey()];
                i++;
            } else
            {
                POP[i] = POPCHILD[e.getKey() - anz];
                i++;
            }
        }
        bewertung(POP, FIT);

    }

    public static int wettkampfSelektion(int[] arr)
    {
        int x = (int) (arr.length * Math.random());
        int y = (int) (arr.length * Math.random());
        if (arr[x] <= arr[y])
        {
            return x;
        } else
        {
            return y;
        }
    }

    public static int gleichverteilteSelektion()
    {
        return (int) (anz * Math.random());
    }

    public static int rouletteSelektion()
    {
        double gesamt = 0;
        double rnd = Math.random();
        for (int i = 0; i < anz; i++)
        {
            gesamt = 1.0 / FIT[i];
        }
        for (int i = 0; i < anz; i++)
        {
            if ((1 / FIT[i]) / gesamt >= rnd)
            {
                return i;
            }
        }
        return 0;
    }

    public static void crossover(int e1, int e2, int childIndex)
    {
        int x = (int) (POP[0].length - (anz / (anz / 20)) - 1 * Math.random()) + (anz / (anz / 20)) + 1;
        for (int i = 0; i < POP[0].length; i++)
        {
            if (i < x)
            {
                POPCHILD[childIndex][i] = POP[e1][i];
                POPCHILD[childIndex + 1][i] = POP[e2][i];
            } else
            {
                POPCHILD[childIndex][i] = POP[e2][i];
                POPCHILD[childIndex + 1][i] = POP[e1][i];
            }
        }
        flipMutation(childIndex);
        flipMutation(childIndex + 1);

    }

    public static void flipMutation(int index)
    {
        for (int i = 0; i < POPCHILD[index].length; i++)
        {
            if (1.0 / anz <= Math.random())
            {
                POPCHILD[index][i] = (POPCHILD[index][i] + 1) % 2;
            }
        }
    }

    public static void swapMutation(int index)
    {
        for (int i = 0; i < POPCHILD[index].length - 1; i++)
        {
            if (1.0 / anz <= Math.random())
            {
                int temp = POPCHILD[index][i];
                POPCHILD[index][i] = POPCHILD[index][i + 1];
                POPCHILD[index][i + 1] = temp;
            }
        }
    }

    public static int getWorst()
    {
        int worstFit = 0;
        int worstIndex = 0;
        for (int i = 0; i < anz; i++)
        {
            if (FIT[i] >= worstFit)
            {
                worstFit = FIT[i];
                worstIndex = i;
            }
        }
        return worstIndex;
    }

    public static void inkrementelleSelektion()
    {
        for(int i = 0; i < POPCHILD.length; i++)
        {
            int same = 0;
            
            for(int j = 0; j < POP.length; j++)
            {
                if(POPCHILD[i] == POP[j])
                {
                    same = 1;
                    break;
                } 
            }
            if(same == 0)
            {
                int temp = getWorst();
                if(POP[temp] != POPCHILD[0])
                {
                    for(int j = 0; j < POP[temp].length; j++)
                    {
                        POP[temp][j] = POPCHILD[i][j];
                    }
                    FIT[temp] = FITCHILD[i];
                }
                else
                {
                    int worstFit = 0;
                    int worstIndex = 0;
                    for (int k = 0; k < anz; k++)
                    {
                        if (FIT[k] >= worstFit && k != temp)
                        {
                            worstFit = FIT[k];
                            worstIndex = k;
                        }
                    }
                    temp = worstIndex;
                    
                    for(int j = 0; j < POP[temp].length; j++)
                    {
                        POP[temp][j] = POPCHILD[i][j];
                    }
                    FIT[temp] = FITCHILD[i];
                }
            }
            
        }
    }

    public static void selektionDerKinder()
    {
        /*
         * int firstE = 0; int lastC = 0, secondLastC = 0; for(int i=0; i < anz; i++) {
         * if(FIT[i] < FIT[firstE]) { firstE = i; }
         * 
         * if(FITCHILD[i] > FITCHILD[lastC]) { secondLastC = lastC; lastC = i; } else {
         * if(FITCHILD[i] > FITCHILD[secondLastC]) { secondLastC = i; } } }
         * 
         * for(int i = 0; i < POP[0].length; i++) { POPCHILD[lastC][i] = POP[firstE][i];
         * if(0.5 < Math.random()) { POPCHILD[secondLastC][i] = 1; } else {
         * POPCHILD[secondLastC][i] = 0; }
         * 
         * }
         */

        for (int i = 0; i < anz; i++)
        {
            for (int j = 0; j < POP[i].length; j++)
            {
                POP[i][j] = POPCHILD[i][j];
            }
            FIT[i] = FITCHILD[i];
        }

    }

    public static void fortpflanzung()
    {
        for (int i = 0; i < anz; i += 2)
        {
            crossover(wettkampfSelektion(FIT), wettkampfSelektion(FIT), i);

        }
        bewertung(POPCHILD, FITCHILD);
        ersetzteMitWettkampf();
    }

    public static void fortpflanzungInkrementell()
    {
        POPCHILD = new int[2][];
        FITCHILD = new int[2];
        crossover(wettkampfSelektion(FIT), wettkampfSelektion(FIT), 0);
        bewertung(POPCHILD, FITCHILD);
        inkrementelleSelektion();
    }
}
