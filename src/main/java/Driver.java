

import com.revature.dao.IUserDao;
import com.revature.dao.UserDao;
import com.revature.models.Role;
import com.revature.models.User;

public class Driver {

	public static void main(String[] args) {
		IUserDao dao = new UserDao();
		//new UserDao().insert();
		User user=new User(0,"username","password","first","last","email@yahoo.com",new Role(1,"standard"));
		dao.insert1(user);
		for(User u: dao.findAll())
		{
			System.out.println(u);
		}
		//ConnectionUtil.getConnection();
	}

}
