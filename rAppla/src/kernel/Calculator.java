package kernel;

public class Calculator 
{	
	public Calculator()
	{
		
	}
	public double getResult(double fak1, double fak2, Operation op)
	{
		switch(op)
		{
		case PLUS:
			return fak1+fak2;
		case MINUS:
			return fak1-fak2;
		case MAL:
			return fak1*fak2;
		case GETEILT:
			return fak1/fak2;
		}
		return 0;
	}
}
