package pageRankAdjacent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class pageRankAdjacent {

	public static void main(String[] args) {
		Map<Integer, Double> pageRankValue = new TreeMap<Integer, Double>();
		
		List<List<Integer>> adjacent = getA();
		Map<Integer, Double> q1 = getQ();
		double alpha = 0.15;
		int iter = 1;
		
		pageRankValue = pageRankMain(adjacent, q1, alpha, iter);

		sortMap(pageRankValue, 4);
	}

	/**
	 * @param adjacent  邻接表
	 * @param q1		初始pageRank向量
	 * @param alpha		参数alpha
	 * @param iter		迭代次数
	 * @return
	 */
	public static Map<Integer, Double> pageRankMain(
			List<List<Integer>> adjacent, Map<Integer, Double> q1,
			double alpha, int iter) {
		Map<Integer, Double> qnew = new TreeMap<Integer, Double>();

		int n = 0;
		do {
			qnew = pageRankA(adjacent, q1, alpha);
			q1 = qnew;
			n++;
		} while (n < iter);
		return qnew;
	}

	/**
	 * 求新的pageRank值向量
	 * 
	 * @param adjacent
	 *            邻接表
	 * @param q
	 *            pageRank值向量
	 * @param alpha
	 *            alpha参数
	 * @return
	 */
	public static Map<Integer, Double> pageRankA(List<List<Integer>> adjacent,
			Map<Integer, Double> q, double alpha) {
		Map<Integer, Double> newQ = new TreeMap<Integer, Double>();
		int size = adjacent.size();
		for (int i = 0; i < size; i++) {
			int lenI = adjacent.get(i).size();
			for (int j = 0; j < lenI; j++) {
				int vartexTo = adjacent.get(i).get(j);
				if (!newQ.containsKey(vartexTo)) {
					newQ.put(vartexTo, q.get(vartexTo) / lenI);
				} else {
					newQ.put(vartexTo, newQ.get(vartexTo) + q.get(vartexTo)
							/ lenI);
				}
			}
		}

		for (int i = 0; i < size; i++) {
			double value = 0;
			if (!newQ.containsKey(i)) {
				value = alpha * 1.0 / size;
			} else {
				value = (1 - alpha) * newQ.get(i) + alpha * 1.0 / size;
			}
			newQ.put(i, value);
		}

		return newQ;
	}

	/**
	 * 计算两个向量的距离
	 * 
	 * @param q1
	 *            第一个向量
	 * @param q2
	 *            第二个向量
	 * @return 它们的距离
	 */
	public static double calDistance(Map<Integer, Double> q1,
			Map<Integer, Double> q2) {
		double sum = 0;

		if (q1.size() != q2.size()) {
			return -1;
		}

		for (int i = 0; i < q1.size(); i++) {
			sum += Math.pow(q1.get(i).doubleValue() - q2.get(i).doubleValue(),
					2);
		}
		return Math.sqrt(sum);
	}

	/**
	 * 对Map按值进行排序，输出top n
	 * @param map
	 * @param top
	 */
	public static void sortMap(Map<Integer, Double> map, int top) {
		List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(
				map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Entry<Integer, Double> o1,
					Entry<Integer, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		int count = 0;
		for (Entry<Integer, Double> mapping : list) {
			System.out.println(mapping.getKey() + ":\t" + mapping.getValue());
			count++;
			if (count >= top) {
				break;
			}
		}
	}
	
	/**
	 * 测试，初始化邻接表
	 * @return
	 */
	public static List<List<Integer>> getA(){
		List<List<Integer>> adjacent = new ArrayList<List<Integer>>();
		List<Integer> row1 = new ArrayList<Integer>();
		row1.add(1);
		row1.add(2);
		row1.add(3);
		List<Integer> row2 = new ArrayList<Integer>();
		row2.add(2);
		row2.add(3);
		List<Integer> row3 = new ArrayList<Integer>();
		row3.add(3);
		List<Integer> row4 = new ArrayList<Integer>();
		row4.add(1);
		adjacent.add(row1);
		adjacent.add(row2);
		adjacent.add(row3);
		adjacent.add(row4);
		return adjacent;
	}

	/**
	 * 测试，初始化pageRank
	 * @return
	 */
	public static Map<Integer, Double> getQ(){
		Map<Integer, Double> q1 = new TreeMap<Integer, Double>();
		q1.put(0, new Double(1));
		q1.put(1, new Double(1));
		q1.put(2, new Double(1));
		q1.put(3, new Double(1));
		return q1;
	}
	
}
