import java.lang.*;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class FirstVisit extends GJNoArguDepthFirst<LinkedList<String>>{

	private HashMap<String, HashMap<String, LinkedList<String>>> class_list;
	private LinkedList<String> list_method;
	private HashMap<String, LinkedList<String>> class_map;

	private Integer value_number;
	//private HashMap<String, LinkedList<String>> class_map;

	public FirstVisit(HashMap<String, HashMap<String, LinkedList<String>>> class_list){//, HashMap<String, LinkedList<String>> class_map){
		this.class_list = class_list;
		//this.class_map = class_map;
	}

	public LinkedList<String> visit(Goal g){
		//String ret = null;

		g.f1.accept(this);            										//type

		return null;
	}

	public LinkedList<String> visit(TypeDeclaration td){
		String ret = null;

		td.f0.accept(this);           										//classdecl

		return null;
	}

	public LinkedList<String> visit(ClassDeclaration cd){

		class_map = new HashMap<>();

		list_method = new LinkedList<>();

		cd.f4.accept(this);        											//method

		class_map.put(cd.f1.f0.toString()+"virtual table", list_method);

		value_number = 1;

		cd.f3.accept(this);

		class_list.put(cd.f1.f0.toString(), class_map);

		return null;
	}

	public LinkedList<String> visit(MethodDeclaration md){

		list_method.add(md.f2.f0.toString());
		return null;

	}

	public LinkedList<String> visit(VarDeclaration vd){

		LinkedList<String> list = new LinkedList<>();

		list.add(value_number.toString());

		value_number = value_number + 1;

		class_map.put(vd.f1.f0.toString(),list);
		
		return null;
	}
}  