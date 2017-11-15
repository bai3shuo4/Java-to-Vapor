import java.lang.*;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class SecondVisit extends GJNoArguDepthFirst<Integer>{

	HashMap<String, HashMap<String, LinkedList<String>>> class_list;
	Integer lable;

	public SecondVisit(HashMap<String, HashMap<String, LinkedList<String>>> class_list){
		this.class_list = class_list;
	}

	public Integer visit(Goal g){

		g.f0.accept(this);
		return null;
	}

	public Integer visit(MainClass mc){

		System.out.println("func Main()");

		lable = 0;

		mc.f15.accept(this);

		System.out.println("ret");

		return null;

	}

	public Integer visit(PrintStatement ps){
		Integer tmp = ps.f2.accept(this);
		System.out.println("PrintIntS(t." + tmp.toString() + ")");
		return tmp;
	}

	public Integer visit(Expression e){

		Integer tmp = e.f0.accept(this);
		return tmp;

	}

	public Integer visit(PlusExpression pe){

		Integer tmp1 = pe.f0.accept(this);
		Integer tmp2 = pe.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "Add(" + "t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		return lable;
	}

	public Integer visit(MinusExpression me){

		Integer tmp1 = me.f0.accept(this);
		Integer tmp2 = me.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "Sub(" + "t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		return lable;
	}

	public Integer visit(TimesExpression te){

		Integer tmp1 = te.f0.accept(this);
		Integer tmp2 = te.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "Muls(" + "t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		return lable;
	}



	public Integer visit(PrimaryExpression pe){

		Integer tmp = pe.f0.accept(this);
		return tmp;
	}

	public Integer visit(IntegerLiteral il){

		Integer tmp = lable;
		System.out.println("t." + lable.toString() + " = " + il.f0.toString());
		lable = lable + 1;
		return tmp;
	}




}