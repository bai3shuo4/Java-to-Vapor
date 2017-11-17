import java.lang.*;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class SecondVisit extends GJNoArguDepthFirst<Integer>{

	HashMap<String, HashMap<String, LinkedList<String>>> class_list;
	HashMap<String, LinkedList<String>> class_map;
	LinkedList<String> method_list;
	LinkedList<String> parameter_list;

	HashMap<String, HashMap<String, LinkedList<String>>> inner_class_list;
	HashMap<String, LinkedList<String>> inner_class_map;

	String curr_classname;
	Integer lable;
	Integer if_lable;
	Integer while_lable;

	boolean call_function;
	boolean inner_call;
	boolean inner_classt;

	public SecondVisit(HashMap<String, HashMap<String, LinkedList<String>>> class_list){
		this.class_list = class_list;
		this.inner_class_list = class_list;
		if_lable = 0;
		while_lable = 0;
		call_function = false;
		inner_call = false;
	}

	public Integer visit(Goal g){

		g.f0.accept(this);
		g.f1.accept(this);

		return null;
	}

	public Integer visit(TypeDeclaration td){

		td.f0.accept(this);

		return null;
	}

	public Integer visit(ClassDeclaration cd){

		curr_classname = cd.f1.f0.toString();

		inner_class_map = new HashMap<>();
		inner_class_map = inner_class_list.get(curr_classname);
		//method_list = class_map.get(class_name + "virtual table");

		cd.f4.accept(this);
		//lable = 0;

		return null;

	}

	public Integer visit(MethodDeclaration md){
		lable = 0;
		System.out.print("func" + " " + curr_classname + "." + md.f2.f0.toString() + "(this ");
		md.f4.accept(this);
		System.out.print(")");
		System.out.println();

		md.f8.accept(this);

		System.out.println("ret" + " " + "t." + md.f10.accept(this).toString());

		return null;
	}

	public Integer visit(FormalParameterList fp){
		fp.f0.accept(this);
		fp.f1.accept(this);
		return null;
	}

	public Integer visit(FormalParameter fp){
		System.out.print(fp.f1.f0.toString() + " ");
		return null;
	}

	public Integer visit(FormalParameterRest fp){
		fp.f1.accept(this);
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

	public Integer visit(AssignmentStatement as){
		Integer tmp = as.f2.accept(this);

		if(!inner_class_map.isEmpty() && inner_class_map.containsKey(as.f0.f0.toString())){
			LinkedList<String> value_list = new LinkedList<>();
			value_list = inner_class_map.get(as.f0.f0.toString());
			String value = value_list.get(0);
			Integer index = Integer.valueOf(value)*4;
			System.out.println("[this + " + index.toString() + "] = t." + tmp.toString());
			return null;
		}
		System.out.println(as.f0.f0.toString() + " = " + "t." + tmp.toString());												//class may be wrong????

		return null;
	}

	public Integer visit(IfStatement is){
		Integer tmp = is.f2.accept(this);

		System.out.println("if0" + " t." + tmp.toString() + " goto :if" + if_lable.toString() + "_else");
		//System.out.print("	");
		is.f4.accept(this);
		System.out.println("goto :if" + if_lable.toString() + "_end");
		System.out.println("if" + if_lable.toString() + "_else:");
		is.f6.accept(this);
		System.out.println("if" + if_lable.toString() + "_end:");

		if_lable = if_lable + 1;
		return null;
	}

	public Integer visit(WhileStatement ws){

		System.out.println("while" + while_lable.toString() + "_top:");
		Integer tmp = ws.f2.accept(this);
		System.out.println("if0 t." + tmp.toString() + " goto :while" + while_lable.toString() + "_end");
		ws.f4.accept(this);
		System.out.println("goto :while" + while_lable.toString() + "_top");
		System.out.println("while" + while_lable.toString() + "_end:");

		while_lable = while_lable + 1;
		return null;
	}

	public Integer visit(CompareExpression ce){
		Integer tmp1 = ce.f0.accept(this);
		Integer tmp2 = ce.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "LtS(t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		Integer tmp = lable;
		lable = lable + 1;
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

		Integer tmp = lable;
		lable = lable + 1;
		return tmp;
	}

	public Integer visit(MinusExpression me){

		Integer tmp1 = me.f0.accept(this);
		Integer tmp2 = me.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "Sub(" + "t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		Integer tmp = lable;
		lable = lable + 1;
		return tmp;
	}

	public Integer visit(TimesExpression te){

		Integer tmp1 = te.f0.accept(this);
		Integer tmp2 = te.f2.accept(this);

		System.out.println("t." + lable.toString() + " = " + "MulS(" + "t." + tmp1.toString() + " " + "t." + tmp2.toString() + ")");

		Integer tmp = lable;
		lable = lable + 1;
		return tmp;
	}

	public Integer visit(BracketExpression be){

		Integer tmp = be.f1.accept(this);
		return tmp;
	}

	public Integer visit(MessageSend ms){
		Integer tmp1 = ms.f0.accept(this);

		if(inner_call){
			String method = ms.f2.f0.toString();
			int index = method_list.indexOf(method);
			call_function = true;

			System.out.println("t." + lable.toString() + " = " + "[this]");
			System.out.println("t." + lable.toString() + " = " + "[" + "t." + lable.toString() + "+" + Integer.toString(index*4) + "]");
			Integer method_lable = lable;
			lable = lable + 1;

			parameter_list = new LinkedList<>();
			ms.f4.accept(this);							//read parameter
			

			System.out.print("t." + lable.toString() + " = " + "call t." + method_lable.toString() + "(this ");
			for(String s : parameter_list) System.out.print(s + " ");
			System.out.print(")");
			System.out.println();

			call_function = false;
			inner_call = false;
			Integer tmp = lable;
			lable = lable + 1;
			return tmp;
		}

		//Integer tmp1 = ms.f0.accept(this);								//t.0
		String method = ms.f2.f0.toString();

		int index = method_list.indexOf(method);
		call_function = true;

		System.out.println("t." + lable.toString() + " = " + "[" + "t." + tmp1.toString() + "]");
		System.out.println("t." + lable.toString() + " = " + "[" + "t." + lable.toString() + "+" + Integer.toString(index*4) + "]");
		Integer method_lable = lable;
		lable = lable + 1;

		parameter_list = new LinkedList<>();
		ms.f4.accept(this);							//read parameter
		
		System.out.print("t." + lable.toString() + " = " + "call t." + method_lable.toString() + "(t." + tmp1.toString() + " ");
		for(String s : parameter_list) System.out.print(s + " ");
		System.out.print(")");
		System.out.println();

		call_function = false;
		Integer tmp = lable;
		lable = lable + 1;
		return tmp;
		
		//Integer tmp2 = ms.f2.accept(this);
	}

	public Integer visit(ExpressionList el){

		//if(call_function){
			Integer tmp1 = el.f0.accept(this);
			parameter_list.add("t." + tmp1.toString());
			el.f1.accept(this);
			return null;
		//}
	}

	public Integer visit(ExpressionRest er){

		Integer tmp1 = er.f1.accept(this);
		parameter_list.add("t." + tmp1.toString());
		return null;
	}



	public Integer visit(PrimaryExpression pe){

		Integer tmp = pe.f0.accept(this);
		return tmp;
	}

	public Integer visit(ThisExpression te){

		inner_call = true; 
		return null;
	}

	public Integer visit(AllocationExpression ae){
		Integer tmp = lable;										//class lable
		String class_name = ae.f1.f0.toString();
		class_map = class_list.get(class_name);
		int size = class_map.size() * 4;

		method_list = class_map.get(class_name + "virtual table");

		System.out.println("t." + lable.toString() + " = " + "HeapAllocZ(" + Integer.toString(size) + ")");
		System.out.println("[t." + lable.toString() + "] = :" + "vmt_" + class_name);

		System.out.println("if t." + lable.toString() + " goto :null" + (if_lable).toString());							//if_lable

		System.out.println("	Error(\"null pointer\")");
		System.out.println("null" + (if_lable).toString() + ":");

		if_lable = if_lable + 1;

		lable = lable + 1;
		return tmp;
	}

	public Integer visit(IntegerLiteral il){

		//if(call_function){
		//	System.out.print(il.f0.toString() + " ");
		//	return null;
		//}

		//else{
			Integer tmp = lable;

			System.out.println("t." + lable.toString() + " = " + il.f0.toString());

			lable = lable + 1;

			return tmp;
		//}
	}

	public Integer visit(Identifier i){

		/////////////////////
		//if map has identifier another treat
		//should be departed into classmap and method map////
		////////////////////
		//if(call_function){
		//	System.out.print(i.f0.toString() + " ");
		//	return null;
		//}

		//else{
		Integer tmp = lable;

		if(!inner_class_map.isEmpty() && inner_class_map.containsKey(i.f0.toString())){
			LinkedList<String> value_list = new LinkedList<>();
			value_list = inner_class_map.get(i.f0.toString());
			String value = value_list.get(0);
			Integer index = Integer.valueOf(value)*4;
			System.out.println("t." + lable.toString() + " = " + "[this + " + index.toString() + "]");

			lable = lable + 1;
			return tmp;
		}
			

			System.out.println("t." + lable.toString() + " = " + i.f0.toString());

			lable = lable + 1;
			return tmp;
		//}
	}




}