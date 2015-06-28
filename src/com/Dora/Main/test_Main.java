package com.Dora.Main;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class test_Main {

	public static void main(String[] args[]) {

		// 创建使用OWL语言的内存模型
		OntModel ontModel = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		// 读取当前路径下的文件，加载模型
		ontModel.read("Creature.owl");

		// 定义一个类作为模型中Animal类的等等价类，并添加注释
		OntClass ontcls = ontModel.createClass(":DongwuClass");
		ontcls.addComment("the EquivalentClass of Animal...", "EN");

		// 通过完整的URI取得模型中的Animal类
		OntClass oc = ontModel
				.getOntClass("http://www.semanticweb.org/dora/ontologies/2015/5/untitled-ontology-4#Animal");
		oc.addEquivalentClass(ontcls); // 将先前定义的类添加为Animal的等价类

		for (Iterator i = ontModel.listClasses(); i.hasNext();) {
			OntClass c = (OntClass) i.next();
			if (!c.isAnon()) {// 如果不是匿名类，则打印类的名字
				System.out.println("Class");
				// 获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
				System.out.println(c.getModel().getGraph().getPrefixMapping()
						.shortForm(c.getURI()));
				// 处理Animal类
				if (c.getLocalName().equals("Animal")) { // 如果当前类是Animal
					System.out.println("  URI@" + c.getURI()); // 输出它的完整URI
					// 取得它的的等价类并打印
					System.out.print("  Animal's EquivalentClass is "
							+ c.getEquivalentClass());
					// 输出等价类的注释
					System.out.println(" [comments:"
							+ c.getEquivalentClass().getComment("EN") + "]");
				}// 处理Animal结束
			}

		}
	}
}
