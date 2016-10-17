import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int i;
	Inven list[] = new Inven[20];
	
	System.out.println("이동하고 싶은 메뉴 번호를 입력해 주세요");
	i = scan.nextInt();	
	switch(i)
	{	
	case 1: System.out.println("1. 재고 입력하기");
			Input(); break;
			
	
	
	}
	

	
		System.out.println("카테고리 입력");
		list[0].cate = scan.next();
		
		System.out.println(list[0].cate);
	}
	
	public static void Input(){
		Inven list[] = new Inven[50];
		for(int i=0; i<10; i++)
		{
			
			
			
		}
		
		

}
}
	