import java.lang.*;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class SecondVisit extends GJNoArguDepthFirst<Integer>{

	HashMap<String, HashMap<String, LinkedList<String>>> class_list;
	HashMap<String, LinkedList<String>> class_map;
	LinkedList<String> method_list;
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

	public Integer visit(Statement s){

		s.f0.accept(this);
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

	public Integer visit(MessageSend ms){

		Integer tmp1 = ms.f0.accept(this);								//t.0
		String method = ms.f2.f0.toString();

		int index = method_list.indexOf(method);

		System.out.println("t." + lable.toString() + " = " + "[" + "t." + tmp1.toString() + "]");
		System.out.println("t." + lable.toString() + " = " + "[" + "t." + lable.toString() + "+" + Integer.toString(index) + "]");
		Integer method_lable = lable;
		lable = lable + 1;
		System.out.println("t." + lable.toString() + " = " + "call t." + method_lable.toString() + "(t." + tmp1.toString() + ")");

		Integer tmp = lable;
		return tmp;
		
		//Integer tmp2 = ms.f2.accept(this);
	}



	public Integer visit(PrimaryExpression pe){

		Integer tmp = pe.f0.accept(this);
		return tmp;
	}

	public Integer visit(AllocationExpression ae){
		Integer tmp = lable;										//class lable
		String class_name = ae.f1.f0.toString();
		class_map = class_list.get(class_name);
		int size = class_map.size() * 4;

		method_list = class_map.get(class_name + "virtual table");

		System.out.println("t." + lable.toString() + " = " + "HeapAllocZ(" + Integer.toString(size) + ")");
		System.out.println("[t." + lable.toString() + "] = :" + "vmt_" + class_name);
		System.out.println("if t." + lable.toString() + " goto :null" + (++lable).toString());
		System.out.println("	Error(\"null pointer\")");
		System.out.println("null" + lable.toString() + ":");
		return tmp;
	}

	public Integer visit(IntegerLiteral il){

		Integer tmp = lable;

		System.out.println("t." + lable.toString() + " = " + il.f0.toString());

		lable = lable + 1;
		return tmp;
	}




}