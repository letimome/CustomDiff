package customs.utils;

public class NavigationMapGenerator {
	 private static String pathToResource = "./src/main/resources/static/";
	
	
	
	
	public static void generateNavigationMapForAlluvial() {
		String template = 
			"	var simple_chart_config = { \n"+
					"  chart: { \n"+
					"   container: '#tree-simple', \n"+
		"		        	connectors: {\n"+
		"			                type: 'step' \n"+
		"			            },\n"+
		"			            node: {"+"\n"+
		"			                HTMLclass: 'nodeExample1'"+"\n"+
		"			            }"+"\n"+
		"			    },    "+"\n"+
		"			    nodeStructure: {"+"\n"+
		"			        text: { name: 'diff(features, product-portfolio)' },"+"\n"+
		"			        HTMLclass: 'blue',"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+"feature"+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			                	 text: { name: 'diff ("+"core-asset"+", "+"product"+")' },"+"\n"+
		"			        //        	 image: 'images/mini-diff.png'"+"\n"+
		"			                } ]"+"\n"+
		"			            },"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff(features,"+"product"+")' },"+"\n"+
		"			          //      image: 'images/mini-product-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+"core-asset"+", "+"product"+")' },"+"\n"+
		"			            //   	 	image: 'images/mini-VP-Treemap.png',"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+"core-asset"+", "+"product"+")[Expression]' },"+"\n"+
		"			              // 			image: 'images/mini-diff.png'"+"\n"+
		"			               		}]"+"\n"+
		"			                } ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}
	
	
	public static void generateNavigationMapForFeatureSide(String feature, String fileName, String expression, String pr) {
		String color="blue";
		if (fileName.equals("core-asset")) color="white";
		else color="blue";
		
		String template = 
			"	var simple_chart_config = { \n"+
					"  chart: { \n"+
					"   container: '#tree-simple', \n"+
		"		        	connectors: {\n"+
		"			                type: 'step' \n"+
		"			            },\n"+
		"			            node: {"+"\n"+
		"			                HTMLclass: 'nodeExample1'"+"\n"+
		"			            }"+"\n"+
		"			    },    "+"\n"+
		"			    nodeStructure: {"+"\n"+
		"			        text: { name: 'diff(features, product-portfolio)' },"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+feature+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		 							"HTMLclass: 'blue',"+"\n"+
		"			                children: [{"+"\n"+
		"			                	 text: { name: 'diff ("+fileName+", "+pr+")["+expression+"]' },"+"\n"+
		"			        //        	 image: 'images/mini-diff.png'"+"\n"+
		"								HTMLclass: '"+color+"',"+"\n"+
		"			                } ]"+"\n"+
		"			            },"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff(features,"+"product"+")' },"+"\n"+
		"			          //      image: 'images/mini-product-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+"core-asset"+", "+"product"+")' },"+"\n"+
		"			            //   	 	image: 'images/mini-VP-Treemap.png',"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+"core-asset"+", "+"product"+")[Expression]' },"+"\n"+
		"			              // 			image: 'images/mini-diff.png'"+"\n"+
		"			               		}]"+"\n"+
		"			                } ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}

	
	public static void generateNavigationMapForProductSide(String feature, String fileName, String expression, String pr) {
		String colormiddle,colorMain, colordiff;
		
		if(expression!=null && expression.contains("'"))
			expression = expression.replace("'", "");

		if (feature.equals("features")) colorMain="blue";
		else colorMain="white";
		
		if (fileName.equals("core-asset")) {
			colordiff="white";
			colormiddle="whithe";
		}
		else {
			colordiff="blue";
			colormiddle="blue";
			colorMain="white";
		} 
		if(expression==null)
			colordiff="white";
		
		String template = 
			"	var simple_chart_config = { \n"+
					"  chart: { \n"+
					"   container: '#tree-simple', \n"+
		"		        	connectors: {\n"+
		"			                type: 'step' \n"+
		"			            },\n"+
		"			            node: {"+"\n"+
		"			                HTMLclass: 'nodeExample1'"+"\n"+
		"			            }"+"\n"+
		"			    },    "+"\n"+
		"			    nodeStructure: {"+"\n"+
		"			        text: { name: 'diff(features, product-portfolio)' },"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+"feature"+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			                	 text: { name: 'diff ("+"core-asset"+", "+"product"+")["+"Expression"+"]' },"+"\n"+
		"			        //        	 image: 'images/mini-diff.png'"+"\n"+
		"			                } ]"+"\n"+
		"			            },"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff(features,"+pr+")' },"+"\n"+
		"			          //      image: 'images/mini-product-treemap.png',"+"\n"+
		" 								HTMLclass: '"+colorMain+"',"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+fileName+", "+pr+")' },"+"\n"+
		"			            //   	 	image: 'images/mini-VP-Treemap.png',"+"\n"+
		" 								HTMLclass: '"+colormiddle+"',"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+fileName+", "+pr+")["+expression+"]' },"+"\n"+
		" 									HTMLclass: '"+colordiff+"',"+"\n"+
		"			              // 		image: 'images/mini-diff.png'"+"\n"+
		"			               		}]"+"\n"+
		"			                } ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}



	public static void generateNavigationMapForFeatureProduct(String featurenamemodified, String pr, String string,
			String string2) {
		
		
	}

}
