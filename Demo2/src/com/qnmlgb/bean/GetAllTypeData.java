package com.qnmlgb.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.ninexiu.wjw.R;

public class GetAllTypeData {
	
	private static GetAllTypeData mGetAllTypeData = null;

	public static GetAllTypeData getInstance() {
		if (mGetAllTypeData == null) {
			mGetAllTypeData = new GetAllTypeData();
		}
		return mGetAllTypeData;
	}

	public static ArrayList<QuickData> getQuickData() {
		ArrayList<QuickData> datas = new ArrayList<QuickData>();
		datas.add(new QuickData("阿萨姆奶茶"));
		datas.add(new QuickData("阿三"));
		datas.add(new QuickData("阿帕奇"));
		datas.add(new QuickData("阿西吧"));
		datas.add(new QuickData("BBQ"));
		datas.add(new QuickData("逼格"));
		datas.add(new QuickData("绷戴"));
		datas.add(new QuickData("陈丽欣"));
		datas.add(new QuickData("陈慧娴"));
		datas.add(new QuickData("菜惠康"));
		datas.add(new QuickData("菜包子"));
		datas.add(new QuickData("草泥马"));
		datas.add(new QuickData("巴赫"));
		datas.add(new QuickData("赵日天"));
		datas.add(new QuickData("李伟"));
		datas.add(new QuickData("张三"));
		datas.add(new QuickData("阿四"));
		datas.add(new QuickData("段誉"));
		datas.add(new QuickData("段正淳"));
		datas.add(new QuickData("张三丰"));
		datas.add(new QuickData("陈坤"));
		datas.add(new QuickData("林俊杰1"));
		datas.add(new QuickData("陈坤2"));
		datas.add(new QuickData("王二a"));
		datas.add(new QuickData("林俊杰a"));
		datas.add(new QuickData("张四"));
		datas.add(new QuickData("林俊杰"));
		datas.add(new QuickData("王二"));
		datas.add(new QuickData("王二b"));
		datas.add(new QuickData("赵四"));
		datas.add(new QuickData("杨坤"));
		datas.add(new QuickData("赵子龙"));
		datas.add(new QuickData("杨坤1"));
		datas.add(new QuickData("李伟1"));
		datas.add(new QuickData("A宋江"));
		datas.add(new QuickData("B宋江1"));
		datas.add(new QuickData("C李伟3"));
		datas.add(new QuickData("D智俪"));
		datas.add(new QuickData("E梅生"));
		datas.add(new QuickData("F子瑞三"));
		datas.add(new QuickData("G长清"));
		datas.add(new QuickData("H子阳"));
		datas.add(new QuickData("I智贤"));
		datas.add(new QuickData("J仲郢"));
		datas.add(new QuickData("K仲天"));
		datas.add(new QuickData("L周永"));
		datas.add(new QuickData("M孜轩"));
		datas.add(new QuickData("N兆祥"));
		datas.add(new QuickData("O籽垚"));
		datas.add(new QuickData("P子夫"));
		datas.add(new QuickData("Q卓林"));
		datas.add(new QuickData("R周爽二"));
		datas.add(new QuickData("S智宁"));
		datas.add(new QuickData("T赵四"));
		datas.add(new QuickData("U杨坤"));
		datas.add(new QuickData("V赵子龙"));
		datas.add(new QuickData("W冠霖"));
		datas.add(new QuickData("X子惟"));
		datas.add(new QuickData("Y宋江"));
		datas.add(new QuickData("Z仲名"));
		datas.add(new QuickData("智善"));
		datas.add(new QuickData("晓鸿"));
		datas.add(new QuickData("A亚鹏"));
		datas.add(new QuickData("呈祥"));
		datas.add(new QuickData("D智露"));
		datas.add(new QuickData("C维涛"));
		datas.add(new QuickData("C文旭"));
		datas.add(new QuickData("G子恒"));
		datas.add(new QuickData("永年"));
		datas.add(new QuickData("兆平"));
		datas.add(new QuickData("汪经纬"));
		datas.add(new QuickData("陆依娜"));
		datas.add(new QuickData("王丽欣"));
		datas.add(new QuickData("狄仁杰"));
		datas.add(new QuickData("郭松"));
		datas.add(new QuickData("吴天庭"));
		datas.add(new QuickData("叶良辰"));
		datas.add(new QuickData("赵日天"));
		datas.add(new QuickData("何澹澹"));
		datas.add(new QuickData("赵丰茂"));
		datas.add(new QuickData("钱清越"));
		datas.add(new QuickData("孙乐康"));
		datas.add(new QuickData("李文彬"));
		datas.add(new QuickData("周嘉言"));
		datas.add(new QuickData("吴可佳"));
		datas.add(new QuickData("郑清婉"));
		datas.add(new QuickData("王望舒"));
		datas.add(new QuickData("刘安石"));
		datas.add(new QuickData("吴力宏"));
		datas.add(new QuickData("八宝强"));
		datas.add(new QuickData("汪祖蓝"));
		datas.add(new QuickData("汪思聪"));
		datas.add(new QuickData("汪精卫"));
		datas.add(new QuickData("汪坚强"));
		datas.add(new QuickData("汪成功"));
		datas.add(new QuickData("汪青春"));
		datas.add(new QuickData("王丽猩"));
		datas.add(new QuickData("王丽欣猩"));
		datas.add(new QuickData("王尼玛"));
		datas.add(new QuickData("王大头"));
		datas.add(new QuickData("王伦苏"));
		datas.add(new QuickData("王日天"));
		return datas;
	}

	public static int getImageViewId(int position) {
		int id = R.drawable.default_404;
		switch (position) {
		case 0:
			id = R.drawable.dog1;
			break;
		case 1:
			id = R.drawable.dudu1;
			break;
		case 2:
			id = R.drawable.dudu5;
			break;
		case 3:
			id = R.drawable.dudu2;
			break;
		case 4:
			id = R.drawable.dog5;
			break;
		case 5:
			id = R.drawable.dog18;
			break;
		case 6:
			id = R.drawable.dog7;
			break;
		case 7:
			id = R.drawable.dog8;
			break;
		case 8:
			id = R.drawable.dog9;
			break;
		case 9:
			id = R.drawable.dog19;
			break;
		case 10:
			id = R.drawable.dog21;
			break;
		case 11:
			id = R.drawable.dog12;
			break;
		case 12:
			id = R.drawable.dog13;
			break;
		case 13:
			id = R.drawable.dog14;
			break;
		case 14:
			id = R.drawable.dog15;
			break;
		case 15:
			id = R.drawable.dog16;
			break;
		case 16:
			id = R.drawable.dudu4;
			break;
		case 17:
			id = R.drawable.dog20;
			break;
		case 18:
			id = R.drawable.love_backgroundimg;
			break;
		case 19:
			id = R.drawable.flowermore;
			break;
		case 20:
			id = R.drawable.flowermore2;
			break;
		case 21:
			id = R.drawable.flowermore3;
			break;
		case 22:
			id = R.drawable.flowermore4;
			break;
		case 23:
			id = R.drawable.flowermore5;
			break;
		case 24:
			id = R.drawable.flowermore6;
			break;
		case 25:
			id = R.drawable.person14;
			break;
		case 26:
			id = R.drawable.dudu6;
			break;
		case 27:
			id = R.drawable.dudu7;
			break;
		default:
			break;
		}
		return id;
	}

	public static ArrayList<Integer> getDataOne() {

		ArrayList<Integer> data = new ArrayList<Integer>();

		data.add(R.drawable.dog1);
		data.add(R.drawable.dog3);
		data.add(R.drawable.dog4);
		data.add(R.drawable.dog5);
		data.add(R.drawable.dog7);
		data.add(R.drawable.dog8);
		data.add(R.drawable.dog9);
		data.add(R.drawable.dog11);
		data.add(R.drawable.dog12);
		data.add(R.drawable.dog13);
		data.add(R.drawable.dog14);
		data.add(R.drawable.dog15);
		return data;
	}

	public static ArrayList<Integer> getDataTwo() {

		ArrayList<Integer> data = new ArrayList<Integer>();
	
		data.add(R.drawable.dog16);
		data.add(R.drawable.dog17);
		data.add(R.drawable.dog18);
		data.add(R.drawable.dog19);
		data.add(R.drawable.dog20);
		data.add(R.drawable.dog21);

		data.add(R.drawable.face1);
		data.add(R.drawable.face2);
		data.add(R.drawable.face3);
		data.add(R.drawable.face4);
		data.add(R.drawable.face5);
		data.add(R.drawable.face6);

		return data;
	}

	public static ArrayList<Integer> getDataThree() {

		ArrayList<Integer> data = new ArrayList<Integer>();

		data.add(R.drawable.face7);
		data.add(R.drawable.face8);
		data.add(R.drawable.face9);
		data.add(R.drawable.face10);
		data.add(R.drawable.face11);
		data.add(R.drawable.face12);
		data.add(R.drawable.face13);
		data.add(R.drawable.face14);
		data.add(R.drawable.face15);
		data.add(R.drawable.dudu1);
		data.add(R.drawable.dudu2);
		data.add(R.drawable.dudu3);

		return data;
	}

	public static ArrayList<Integer> getDataFour() {

		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(R.drawable.dudu4);
		data.add(R.drawable.dudu5);
		data.add(R.drawable.dudu6);
		data.add(R.drawable.person4);
		data.add(R.drawable.person7);
		data.add(R.drawable.person9);
		data.add(R.drawable.person10);
		data.add(R.drawable.person11);
		data.add(R.drawable.person12);
		data.add(R.drawable.person13);
		data.add(R.drawable.person14);
		data.add(R.drawable.person16);

		return data;
	}

	public int[] getImgIDs() {
		int[] imgIDs = new int[] { R.drawable.dog8, R.drawable.dog12,
				R.drawable.dog13, R.drawable.dog11, R.drawable.dog14,
				R.drawable.dog15, R.drawable.dog16, R.drawable.dog20,
				R.drawable.dog21, R.drawable.face10 };

		return imgIDs;
	}
	
	public static ArrayList<String> getAllNetImageUrl(){
		ArrayList<String> datas = new ArrayList<String>();
		datas.add("http://img.popoho.com/allimg/120505/155K040F-5.jpg");
		datas.add("http://www.jf258.com/uploads/2014-09-08/104734923.jpg");
		datas.add("http://www.jf258.com/uploads/2014-09-08/104735285.jpg");
		datas.add("http://p.3761.com/pic/84201393378242.jpg");
		datas.add("http://img1.imgtn.bdimg.com/it/u=1845733812,3638365823&fm=21&gp=0.jpg");
		datas.add("http://img1.imgtn.bdimg.com/it/u=751287459,248212762&fm=21&gp=0.jpg");
		datas.add("http://wenwen.soso.com/p/20110203/20110203183642-1333991527.jpg");
		datas.add("http://p.3761.com/pic/30211404779805.jpg");
		datas.add("http://a.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=714fa609d31373f0f56a6799943f67c3/6d81800a19d8bc3e113938b8808ba61ea8d3458a.jpg");
		datas.add("http://p1.gexing.com/touxiang/20120828/2327/503ce363497d3.jpg");
		datas.add("http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=6819a231d139b6004d9b07b3dc60191c/b21c8701a18b87d65aa80acd030828381e30fd7e.jpg");
		return datas;
	}

}
