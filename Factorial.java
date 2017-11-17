class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac());
    }
}

class Fac {
    int x;
    public int ComputeFac(){
        int num_aux;
        num_aux = 1;

        while(num_aux < 5){num_aux = num_aux + 1;}
        return num_aux ;
    }
}
