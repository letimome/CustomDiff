package customs.utils;

public class NavigationMapGenerator {
	 private static String pathToResource = "./src/main/resources/static/";
	
	static String productPortfolioToken = "Product Variants";
	static String productToken = "Product Variant";
	static String productAssetToken = "Variant asset";
	static String coreAssetToken = "PL asset";
	static String coreAssetsToken = "PL assets";
	static String expressionToken= "Cond. Expression";
	static String featuresToken = "PL Features";
	static String featureToken = "PL Feature";
	
	
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
		"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
		"			        HTMLclass: 'orange',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
		"							children: [{"+"\n"+
		"			               	   text: { },"+"\n"+	
				"			                children: [{"+"\n"+
				"			                	 text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+")["+expressionToken+"]' },"+"\n"+
		
				"			                } ]"+"\n"+
		"			                } ]"+"\n"+
		"			            },"+"\n"+

		"			            {"+"\n"+
		"			                text: { name: 'diff("+featuresToken+","+productToken+")' },"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+coreAssetToken+", "+productToken+")' },"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+")["+expressionToken+"]' },"+"\n"+
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
			fileName = coreAssetToken;
			expression = expressionToken;
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
		"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"					HTMLclass: 'visitedBlue',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+feature+", "+productPortfolioToken+")' },"+"\n"+
		 							"HTMLclass: 'orange',"+"\n"+
		 							"children: [{"+"\n"+
		 								"text: { },"+"\n"+	
				"			                children: [{"+"\n"+
				"			                	 text: { name: 'diff (PL."+fileName+", "+pr+"."+fileName+")["+expression+"]' },"+"\n"+
				"								HTMLclass: '"+color+"',"+"\n"+
				"			                } ]"+"\n"+
		"							} ]"+"\n"+
		"			            },"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff("+featuresToken+","+productToken+")' },"+"\n"+
		"			          //      image: 'images/mini-product-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+coreAssetToken+", "+productToken+")' },"+"\n"+
		"			            //   	 	image: 'images/mini-VP-Treemap.png',"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+")["+expressionToken+"]' },"+"\n"+
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
			fileName = coreAssetsToken;
			expression = expressionToken;
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
		"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
		"			      //  image: 'images/mini-alluvial.png',"+"\n"+
		"					HTMLclass: 'visitedBlue',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
		"			              //  image: 'images/mini-feature-treemap.png',"+"\n"+
		"			                children: [{"+"\n"+
		
		"			                	 text: { name: 'diff ("+coreAssetToken+", "+productToken+")["+expressionToken+"]' },"+"\n"+
		"			        //        	 image: 'images/mini-diff.png'"+"\n"+
		"			                } ]"+"\n"+
		"			            },"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff("+coreAssetsToken+","+pr+".assets)' },"+"\n"+
		"			          //      image: 'images/mini-product-treemap.png',"+"\n"+
		" 								HTMLclass: '"+colorMain+"',"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff (PL."+fileName+", "+pr+"."+fileName+")' },"+"\n"+
		"			            //   	 	image: 'images/mini-VP-Treemap.png',"+"\n"+
		" 								HTMLclass: '"+colormiddle+"',"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff (PL."+fileName+", "+pr+"."+fileName+")["+expression+"]' },"+"\n"+
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
		System.out.println(fileName);
		System.out.println(expression);
		
		String color="orange";
		if (fileName.equals("core-asset") && expression!=null) {
			color="white";
			visited = "#0B0080";
			fileName = coreAssetsToken;
			expression = expressionToken;
		} 
		else {
			System.out.println("DENTROOOO");
			color="orange";
			visited = "white";
		} 
		System.out.println(fileName);
		System.out.println(expression);
		System.out.println(color);
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
		"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
		"					HTMLclass: 'visitedBlue',"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff ("+featureToken+"," +productPortfolioToken+")' },"+"\n"+
		 							"HTMLclass: 'white',"+"\n"+
		 							"children: [{"+"\n"+
		"			               	 		text: { name: '' },"+"\n"+
												"children: [{ \n" + 
													"	text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+")["+expressionToken+"]' }, \n" + 
													"	HTMLclass: 'white', \n" + 
												 " }] \n" +
	    "			               		}]"+"\n"+
				 							 
		"			            },"+"\n"+
		"						{\n"+	                
								"	text: { name: '' }, \n" + 
								"	HTMLclass: 'white', \n" + 
								"children: [{ \n" + 
											"text:{name: 'diff("+feature+","+pr+")'},"+ "\n" + 
											"HTMLclass: 'orange', \n" + 
												
												"			     	children: [{ \n" + 
												"			       	 text: { name: 'diff ("+feature+"."+fileName+", "+pr+")["+expression+"]' }, \n" + 
												"					 HTMLclass: '"+color+"', \n" + 
												"					 }] \n" + 
											 " }] \n" +
		"						},\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff("+featuresToken+","+productToken+")' },"+"\n"+
		"			                children: [{"+"\n"+
		"			               	 	text: { name: 'diff ("+coreAssetsToken+", "+productToken+")' },"+"\n"+
		"			               		children: [{"+"\n"+
		"			               	 		text: { name: 'diff ("+coreAssetToken+", "+productToken+")["+expressionToken+"]' },"+"\n"+
		"			               		}]"+"\n"+
		"			                } ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}


}
