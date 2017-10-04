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
		"			        HTMLclass: 'orange',"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+"feature"+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			                	 text: { name: 'diff ("+"core-asset"+", "+"product"+")[Expression]' },"+"\n"+
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
		String visited = "#0B0080";
		
		String color="orange";
		if (fileName.equals("core-asset")) {
			color="white";
			visited = "#0B0080";
		} 
			
		else {
			color="orange";
			visited = "white";
		} 
		
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
		"					HTMLclass: 'visitedBlue',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+feature+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		 							"HTMLclass: 'orange',"+"\n"+
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

		if (feature.equals("features")) colorMain="orange";
		else colorMain="visitedBlue";
		
		if (fileName.equals("core-asset")) {
			colordiff="white";
			colormiddle="white";
		}
		else {
			colordiff="orange";
			colormiddle="orange";
			colorMain="visitedBlue";
		} 
		if(expression==null)
			colordiff="white";
		
		String template = 
			"var simple_chart_config = { \n"+
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
		"					HTMLclass: 'visitedBlue',"+"\n"+
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



	public static void generateNavigationMapForFeatureProduct(String feature, String pr, String fileName, String expression) {

		String visited = "#0B0080";
		
		String color="orange";
		if (fileName.equals("core-asset") || expression!=null || (!expression.equals("Expression"))) {
			color="white";
			visited = "#0B0080";
		} 
		else {
			color="orange";
			visited = "white";
		} 
		
		String template = "	var simple_chart_config = { \n"+
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
		"					HTMLclass: 'visitedBlue',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+feature+", product-portfolio)' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		 							"HTMLclass: 'white',"+"\n"+
		"			                children: [{ \n"+
		"		 							text:{name: 'diff("+feature+","+pr+")'}, \n"+
		"									 HTMLclass: 'orange',\n"+
		"										children: [{"+"\n"+
		"			               				 	 text: { name: 'diff ("+fileName+", "+pr+")["+expression+"]' },"+"\n"+
		"											HTMLclass: '"+color+"',"+"\n"+
		"			                				} ]"+"\n"+
		"							}]"+
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


}
