package pageRankMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class pageRank {

	private static final double ALPHA = 0.85;
	private static final double DISTANCE = 0.0000001;

	public static void main(String[] args) throws IOException {
		List<List<Double>> s = getS();
		List<List<Double>> u = getU();
		List<Double> q1 = new ArrayList<Double>();
		q1.add(new Double(1));
		q1.add(new Double(1));
		q1.add(new Double(1));
		q1.add(new Double(1));

		System.out.println("start pageRank");
		List<Double> pageRank = calPageRank(q1, s, u, ALPHA);

		printVector(pageRank);

	}

	public static List<Double> calPageRank(List<Double> q1,
			List<List<Double>> s, List<List<Double>> u, double a) {
		List<List<Double>> g = getG(s, u, ALPHA);
		List<Double> q = null;
		int count = 1;
		while (true && count <= 1) {
			q = vectorMulMatrix(g, q1);
			double distance = calDistance(q, q1);
			System.out.println("*****第" + count + "次迭代:\t\t\t距离为： " + distance);
			if (distance <= DISTANCE) {
				System.out.println("END, " + "total execute " + count + "次!");
				break;
			}
			q1 = q;
			count++;
		}
		return q;
	}

	/**
	 * q = Gq G = a*s + (1-a)/n*u 获得初始的G矩阵
	 * 
	 * @param a
	 * @return
	 */
	public static List<List<Double>> getG(List<List<Double>> s,
			List<List<Double>> u, double a) {
		int n = s.size();
		List<List<Double>> as = numberMulMatrix(s, a);
		List<List<Double>> anu = numberMulMatrix(u, (1 - a) / n);
		List<List<Double>> g = addMatrix(as, anu);
		return g;
	}

	/**
	 * 计算两个矩阵相加
	 * 
	 * @param list1
	 * @param list2
	 * @return 两个矩阵之和
	 */
	public static List<List<Double>> addMatrix(List<List<Double>> list1,
			List<List<Double>> list2) {
		if (list1.size() != list2.size() || list1.size() <= 0
				|| list2.size() < +0) {
			return null;
		}
		List<List<Double>> list = new ArrayList<List<Double>>();
		for (int i = 0; i < list1.size(); i++) {
			List<Double> temp = new ArrayList<Double>();
			for (int j = 0; j < list1.get(i).size(); j++) {
				double t = list1.get(i).get(j) + list2.get(i).get(j);
				temp.add(t);
			}
			list.add(temp);
		}
		return list;
	}

	/**
	 * 计算一个数乘以矩阵
	 * 
	 * @param m
	 * @param a
	 * @return 矩阵
	 */
	public static List<List<Double>> numberMulMatrix(List<List<Double>> m,
			double a) {
		List<List<Double>> list = new ArrayList<List<Double>>();
		for (int i = 0; i < m.size(); i++) {
			List<Double> temp = new ArrayList<Double>();
			for (int j = 0; j < m.get(i).size(); j++) {
				double t = a * m.get(i).get(j);
				temp.add(t);
			}
			list.add(temp);
		}
		return list;
	}

	/**
	 * 计算一个矩阵乘以一个向量
	 * 
	 * @param m
	 * @param v
	 * @return 结果向量
	 */
	public static List<Double> vectorMulMatrix(List<List<Double>> m,
			List<Double> v) {
		if (m == null || v == null || m.size() <= 0 || m.size() != v.size()) {
			return null;
		}

		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < m.size(); i++) {
			double sum = 0;
			for (int j = 0; j < v.size(); j++) {
				sum += m.get(i).get(j) * v.get(j);
			}
			list.add(sum);
		}
		return list;
	}

	/**
	 * 计算两个向量的距离
	 * 
	 * @param q1
	 * @param q2
	 * @return 欧氏距离值
	 */
	public static double calDistance(List<Double> q1, List<Double> q2) {
		double sum = 0;

		if (q1.size() != q2.size()) {
			return -1;
		}
		for (int i = 0; i < q1.size(); i++) {
			sum += Math.pow(q1.get(i) - q2.get(i), 2);
		}
		return Math.sqrt(sum);
	}

	/**
	 * 打印输出一个向量
	 */
	public static void printVector(List<Double> v) {
		for (int i = 0; i < v.size(); i++) {
			System.out.print(v.get(i) + ",");
		}
		System.out.println();
	}

	 /**
	 * 打印输出一个矩阵
	 */
	 public static void printMatrix(List<List<Double>> m) {
	 for (int i = 0; i < m.size(); i++) {
	 for (int j = 0; j < m.get(i).size(); j++) {
	 System.out.print(m.get(i).get(j) + ",");
	 }
	 System.out.println();
	 }
	 }
	
	 /**
	 * 打印输出一个向量
	 */
	 public static void printVector1(List<Double> v) {
	 for (int i = 0; i < v.size(); i++) {
	 System.out.print(v.get(i) + ",");
	 }
	 System.out.println();
	 }

	/**
	 * 获得也一个初始的随机向量
	 *
	 * @param n
	 * @return 0——5的随机向量
	 */
	public static List<Double> getInitQ(int n) {
		Random random = new Random();
		List<Double> q = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			q.add(new Double(5 * random.nextDouble()));
		}
		return q;
	}

	/**
	 * 初始化S矩阵
	 *
	 * @return S
	 */
	public static List<List<Double>> getS() {
		List<Double> row1 = new ArrayList<Double>();
		row1.add(new Double(0));
		row1.add(new Double(0));
		row1.add(new Double(0));
		row1.add(new Double(0));
		List<Double> row2 = new ArrayList<Double>();
		row2.add(new Double(1 / 3.0));
		row2.add(new Double(0));
		row2.add(new Double(0));
		row2.add(new Double(1));
		List<Double> row3 = new ArrayList<Double>();
		row3.add(new Double(1 / 3.0));
		row3.add(new Double(1 / 2.0));
		row3.add(new Double(0));
		row3.add(new Double(0));
		List<Double> row4 = new ArrayList<Double>();
		row4.add(new Double(1 / 3.0));
		row4.add(new Double(1 / 2.0));
		row4.add(new Double(1));
		row4.add(new Double(0));

		List<List<Double>> s = new ArrayList<List<Double>>();
		s.add(row1);
		s.add(row2);
		s.add(row3);
		s.add(row4);

		return s;
	}

	/**
	 * 初始化U矩阵，全1
	 *
	 * @return U
	 */
	public static List<List<Double>> getU() {
		List<Double> row1 = new ArrayList<Double>();
		row1.add(new Double(1));
		row1.add(new Double(1));
		row1.add(new Double(1));
		row1.add(new Double(1));
		List<Double> row2 = new ArrayList<Double>();
		row2.add(new Double(1));
		row2.add(new Double(1));
		row2.add(new Double(1));
		row2.add(new Double(1));
		List<Double> row3 = new ArrayList<Double>();
		row3.add(new Double(1));
		row3.add(new Double(1));
		row3.add(new Double(1));
		row3.add(new Double(1));
		List<Double> row4 = new ArrayList<Double>();
		row4.add(new Double(1));
		row4.add(new Double(1));
		row4.add(new Double(1));
		row4.add(new Double(1));

		List<List<Double>> s = new ArrayList<List<Double>>();
		s.add(row1);
		s.add(row2);
		s.add(row3);
		s.add(row4);

		return s;
	}

}
