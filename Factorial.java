class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac());
    }
}

class Fac {
    int x;
    public int ComputeFac(){
        int num_aux;
        boolean t1;
        boolean t2;

        //t= true && false;
        num_aux = 1;
        t1 = true;
        t2 = false;

        t1 = t1 && t2;
        if(!t1){
            num_aux = num_aux + 1;}
        else{
            num_aux = num_aux;
        }
        return num_aux ;
    }
}
