class Complex  {
    static int counter=0;
    double Re, Im;
    Complex Sum(Complex obj){
        Complex tmp = new Complex();
        tmp.Re=Re+obj.Re;
        tmp.Im=Im+obj.Im;

        return tmp;
    }

    double Length() {
        return Math.sqrt(Math.pow(Im, 2) + Math.pow(Re, 2)); //модуль (длина)
    }

    Complex Divide(Complex obj) {
        if(obj.Im==0||obj.Re==0){
            throw new ArithmeticException();
        }
        else    {
            Complex tmp = new Complex();
            tmp.Re = (Re*obj.Re + Im*obj.Im)/(obj.Re*obj.Re + obj.Im*obj.Im);
            tmp.Im = (Im * obj.Re - Re * obj.Im)/(obj.Re*obj.Re + obj.Im*obj.Im);
            return tmp;
        }
    }

    Complex Multi(Complex obj){
        Complex tmp=new Complex();
        tmp.Re = Re * obj.Re - Im * obj.Im;
        tmp.Im = Re * obj.Im + Im * obj.Re;
        return tmp;
    }

    void show(Complex obj){
        System.out.println("Действительная часть = "+Re);
        System.out.println("Мнимая часть = "+Im);
    }

    Complex() {
        Re=0;
        Im=0;
    }

    Complex(double x){
        Re=x;
        Im=0;
    }

    Complex(double x,double y){
        Re=x;
        Im=y;
    }

    Complex(Complex obj){
        Re=obj.Re;
        Im=obj.Im;
    }
    @Override
    public String toString() {
        return "Complex{" + "Re=" + Re + ", Im=" + Im + '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (Double.doubleToLongBits(this.Re) != Double.doubleToLongBits(other.Re)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Im) != Double.doubleToLongBits(other.Im)) {
            return false;
        }
        return true;
    }
}

