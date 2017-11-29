package customs.utils;

public class NavigationMapGenerator {
	 private static String pathToResource = "./src/main/resources/static/";
	
	static String productPortfolioToken = "Product Portfolio";
	static String productToken = "Product Variant";
	static String productAssetToken = "Variant asset";
	static String coreAssetToken = "PL asset";
	static String coreAssetsToken = "Platform assets";
	static String expressionToken= "Filter";
	static String featuresToken = "Parent Features";
	static String featureToken = "Features";
	static String componentToken = "Components";
	
	
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
			
				"			               children: [{"+"\n"+
						                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
						                	 "children: [{"+"\n"+
				 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
				"			                } ]"+"\n"+
	"									} ]"+"\n"+
		"			            },"+"\n"+
		
		"						{"+"\n"+
		"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
		"							children: [{"+"\n"+
       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
       	 "								children: [{"+"\n"+
"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
"			               				 } ]"+"\n"+
"									} ]"+"\n"+
	
		"			        		},"+"\n"+
		
		"			            {"+"\n"+
		"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
		"							children: [{"+"\n"+
       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
       	 "									children: [{"+"\n"+
"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
"			               					 } ]"+"\n"+
"									} ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}
	
	
	public static void generateNavigationMapForFeatureSide(String feature, String fileName, String expression, String pr, String component) {
		String visited = "#0B0080";
		
		String color="orange";
		if (fileName.equals("core-asset") || fileName.equals("PL asset")) {
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
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+feature+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'orange',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+component+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff (Files, "+productPortfolioToken+")' },"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+fileName+", "+pr+") ["+expression+"]' },"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}
	
	public static void generateNavigationMapForProductSide(String feature, String fileName, String expression, String pr) {
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
				
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					"			                } ]"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
	}



	public static void generateNavigationMapForFeatureProduct(String feature, String pr, String fileName, String expression) {

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
				
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					"			                } ]"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
			FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}


	public static void generateNavigationMapForCodeDiffFP(String feature, String file, String expression, String pr) {		
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
				
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					"			                } ]"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
		
		}


	public static void generateNavigationMapForCodeDiffF(String feature, String file, String expression,
			String pr) {
		
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
			"					HTMLclass: 'visitedBlue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+feature+", "+productPortfolioToken+")' },"+"\n"+
			"							HTMLclass: 'visitedBlue',"+"\n"+
					"			        children: [{"+"\n"+
					"			                	 text: { name: 'diff ("+file+", "+file+") ["+expression+"]' },"+"\n"+
					"							HTMLclass: 'orange',"+"\n"+
					"			                } ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							HTMLclass: 'white',"+"\n"+
			"			               		children: [{"+"\n"+
			"			               	 		text: { name: 'diff ("+coreAssetsToken+", "+coreAssetsToken+") ["+expressionToken+"]' },"+"\n"+
			"									HTMLclass: 'white',"+"\n"+
			"			               		}]"+"\n"+
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							HTMLclass: 'white',"+"\n"+
			"			               		children: [{"+"\n"+
		   	"									text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
			"									HTMLclass: 'white',"+"\n"+
			"								}]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
			FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
		
	}


	public static void generateNavigationMapForCodeDiffP(String feature, String file, String expression, String pr) {
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
				
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					"			                } ]"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
			FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}

	
	/***Simple navigations***/
	public static void generateSimpleNavigationMapForAlluvial() {
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
			
				"			               children: [{"+"\n"+
						                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
						                	 "children: [{"+"\n"+
				 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
				"			                } ]"+"\n"+
	"									} ]"+"\n"+
		"			            },"+"\n"+
		
		"						{"+"\n"+
		"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
       	 "								children: [{"+"\n"+
"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
"									} ]"+"\n"+
	
		"			        		},"+"\n"+
		
		"			            {"+"\n"+
		"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
		"							children: [{"+"\n"+
       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
       	 "									children: [{"+"\n"+
"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
"			               					 } ]"+"\n"+
"									} ]"+"\n"+
		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
		
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
	}
}
