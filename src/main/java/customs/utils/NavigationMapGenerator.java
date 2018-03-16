package customs.utils;

public class NavigationMapGenerator {
	 private static String pathToResource = "./src/main/resources/static/";
	
	static String productPortfolioToken = "Product Portfolio";
	static String productToken = "Product Variant";
	static String productAssetToken = "variant asset";
	static String productAssetsToken = "variant assets";
	static String coreAssetToken = "PL asset";
	static String coreAssetsToken = "PL assets";
	static String expressionToken= "Filter";
	static String parentFeaturesToken = "Parent features";
	static String childFeaturesToken = "Child features";
	static String featuresToken = "Features";
	static String featureToken = "Feature";
	static String parentFeatureToken = "Parent feature";
	static String componentToken = "Component";
	static String componentsToken = "Components";
	
	public static void generateNavigationMapForAlluvialSimple() {
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
			"			                text: { name: 'diff ("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetsToken+", "+productToken+") ["+expressionToken+"]' },"+"\n"+
						"			                } ]"
						+ 			"},"+"\n"+
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	 							
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
		
		
	}
	
	
	
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
			"			        text: { name: 'diff("+parentFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        HTMLclass: 'orange',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+childFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentsToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetsToken+", "+productToken+") ["+expressionToken+"]' },"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+childFeaturesToken+", "+productToken+" "+componentsToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productToken+")' },"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+parentFeaturesToken+", "+productToken+" "+componentsToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+parentFeaturesToken+", "+componentToken+" "+productAssetToken+")' },"+"\n"+
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
						 "								text: { name: 'diff ("+fileName+", "+pr+") ["+expression+"]' },"+"\n"+
						"			                } ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
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
			"			        HTMLclass: 'blue',"+"\n"+
			"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
					"			               children: [{"+"\n"+

					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+		            
			"			            }]"
			+ 						"},"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"			 			       HTMLclass: 'orange',"+"\n"+
			"								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
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
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
			
					"			               children: [{"+"\n"+
	//						                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	//						                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					 //			"			                } ]"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"			        HTMLclass: 'orange',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
	       	//"								children: [{"+"\n"+
	       	//"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	       	//"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
			//	 " 								text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
			// 	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	//"			               					 } ]"+"\n"+
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
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
					"			               children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"			 	  	       HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	"			       				 HTMLclass: 'orange',"+"\n"+
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+

	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"			               					 } ]"+"\n"+
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
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
				
					"			               children: [{"+"\n"+
							              
							              
					 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
					
		"									} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 
	       
	"											text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productToken+")' },"+"\n"+
			"			        HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	"							        HTMLclass: 'orange',"+"\n"+
	       	 "									text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	
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


	public static void generateNavigationMapForFeatureSideLevel2(String idfeature, String coreasset, String expression,
			String components, String parentfeature) {
		
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
			"			        text: { name: 'diff("+parentFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+parentfeature+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'blue',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+components+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'orange',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+parentFeaturesToken+", "+componentsToken+")' },"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+parentFeaturesToken+", "+productAssetsToken+")' },"+"\n"+
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
	
	public static void generateNavigationMapForFeatureSideLevel3(String idfeature,
			String components, String files, String parentfeature) {
		
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
			"			                text: { name: 'diff ("+parentfeature+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'blue',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+components+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'blue',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+files+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'orange',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
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
	
	public static void generateNavigationMapForFeatureSideLevel4(String idfeature, String coreasset, String expression,
			String components, String parentfeature, String files, String filename, String productName) {
		
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
			"			                text: { name: 'diff ("+parentfeature+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'blue',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+components+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'blue',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+files+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'blue',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+filename+", "+productName+") ["+expression+"]' },"+"\n"+
						 							"	 HTMLclass: 'orange',"+"\n"+
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


	public static void generateNavigationMapForFeatureProductSideLevel1(String features, String productName) {
		
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
			"			                text: { name: 'diff ("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+features+", "+productName+")' },"+"\n"+
									"   HTMLclass: 'orange',"+"\n"+
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


	public static void generateNavigationMapForFeatureProductSideLevel2(String features, String productName, String componentName) {
		
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
			"			                text: { name: 'diff ("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+features+", "+productName+")' },"+"\n"+
									"   HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+features+", "+productName+"."+componentName+")' },"+"\n"+
	     	"								HTMLclass: 'orange',"+"\n"+
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
	
	public static void generateNavigationMapForFeatureProductSideLevel3(String features, String productName, String componentName, String filename, String expression) {
		
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
			"			                text: { name: 'diff ("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+features+", "+productName+")' },"+"\n"+
									"   HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+features+", "+productName+"."+componentName+")' },"+"\n"+
	     	"								HTMLclass: 'blue',"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+filename+", "+productName+") ["+expression+"]' },"+"\n"+
	"											HTMLclass: 'orange',"+"\n"+
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
	
	public static void generateNavigationMapForProductSideLevel1(String productName) {
		
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
			"			        text: { name: 'diff("+parentFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+childFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentsToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+childFeaturesToken+", "+componentsToken+")' },"+"\n"+
									"   HTMLclass: 'white',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+featureToken+", "+productAssetsToken+")' },"+"\n"+
	     	"								HTMLclass: 'white',"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"											HTMLclass: 'white',"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+parentFeaturesToken+", "+productName+")' },"+"\n"+
			"							HTMLclass: 'orange',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+parentFeaturesToken+", Variant assets)' },"+"\n"+
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
	
	public static void generateNavigationMapForProductSideLevel2(String productName, String componentName) {
		
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
			"			        text: { name: 'diff("+parentFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+childFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentsToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+childFeaturesToken+", "+componentToken+")' },"+"\n"+
									"   HTMLclass: 'white',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+childFeaturesToken+", "+componentToken+")' },"+"\n"+
	     	"								HTMLclass: 'white',"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"											HTMLclass: 'white',"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+parentFeaturesToken+", "+productName+")' },"+"\n"+
			"							HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+parentFeaturesToken+", "+componentName+")' },"+"\n"+
	       	"							HTMLclass: 'orange',"+"\n"+
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
	
	
public static void generateNavigationMapForProductSideLevel3(String productName, String componentName, String fileName, String expression) {
		
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
			"			        text: { name: 'diff("+parentFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"			        HTMLclass: 'blue',"+"\n"+
			"			        children: ["+"\n"+
			"			            {"+"\n"+
			"			                text: { name: 'diff ("+childFeaturesToken+", "+productPortfolioToken+")' },"+"\n"+
			"							 HTMLclass: 'white',"+"\n"+
					"			               children: [{"+"\n"+
							                	 " text: { name: 'diff ("+componentsToken+", "+productPortfolioToken+")' },"+"\n"+
							                "	 HTMLclass: 'white',"+"\n"+
							                	 "children: [{"+"\n"+
								                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
								                	 "	 HTMLclass: 'white',"+"\n"+
								                	 "children: [{"+"\n"+
						 "								text: { name: 'diff ("+coreAssetToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
						 							"	 HTMLclass: 'white',"+"\n"+
						"			                } ]"+"\n"+
		"										} ]"+"\n"+
	"										} ]"+"\n"+
			"			            },"+"\n"+
			
			"						{"+"\n"+
			"			                text: { name: 'diff("+childFeaturesToken+", "+componentsToken+")' },"+"\n"+
									"   HTMLclass: 'white',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+childFeaturesToken+", "+componentToken+")' },"+"\n"+
	     	"								HTMLclass: 'white',"+"\n"+
	       	 "								children: [{"+"\n"+
	"											text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
	"											HTMLclass: 'white',"+"\n"+
	"			               				 } ]"+"\n"+
	"									} ]"+"\n"+
		
			"			        		},"+"\n"+
			
			"			            {"+"\n"+
			"			                text: { name: 'diff("+featuresToken+", "+productName+")' },"+"\n"+
			"							HTMLclass: 'blue',"+"\n"+
			"							children: [{"+"\n"+
	       	 " 								text: { name: 'diff ("+featuresToken+", "+componentName+")' },"+"\n"+
	       	"							HTMLclass: 'blue',"+"\n"+
	       	 "									children: [{"+"\n"+
	"											text: { name: 'diff ("+fileName+", "+fileName+") ["+expression+"]' },"+"\n"+
	"											HTMLclass: 'orange',"+"\n"+
	"			               					 } ]"+"\n"+
	"									} ]"+"\n"+
			"			            }"+"\n"+
			"			        ]"+"\n"+
			"			    }"+"\n"+
			"			};";
			
		FileUtils.writeToFile(pathToResource+"NavigationMap.js", template);
		
	}


/** step by step navigation map***/
public static void generateNavigationMapMainRow(String feature,  String pr, 	String selectedName) {
	
	String rootcolor="blue",leftColor="white", middlecolor="white", rightColor="white";
	
	if (selectedName.equals("root")) {
		rootcolor = "orange";	
	}else {
		if (selectedName.equals("left")) {
			leftColor = "orange";
		}else if (selectedName.equals("right")) {
			rightColor = "orange";
		}else if (selectedName.equals("middle") )
			middlecolor = "orange";
	}
	if (feature==null) feature = featuresToken;
	if (pr==null) pr = productToken;
	
	String templateMainRow = 
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
		"			        HTMLclass: '"+rootcolor+"',"+"\n"+
		"			        text: { name: 'diff("+featuresToken+", "+productPortfolioToken+")' },"+"\n"+
		"			        children: ["+"\n"+
		"			            {"+"\n"+
		   "						HTMLclass: '"+leftColor+"',"+"\n"+
		"			                text: { name: 'diff ("+featureToken+", "+productPortfolioToken+")' },"+"\n"+
		"						},"+"\n"+
		
		"						{"+"\n"+
		"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
		"							HTMLclass: '"+middlecolor+"',"+"\n"+
	
		"			        		},"+"\n"+
		"			            {"+"\n"+
		"			                text: { name: 'diff("+featureToken+", "+productToken+")' },"+"\n"+
		"			 			       HTMLclass: '"+rightColor+"',"+"\n"+

		"			            }"+"\n"+
		"			        ]"+"\n"+
		"			    }"+"\n"+
		"			};";
	FileUtils.writeToFile(pathToResource+"NavigationMap.js", templateMainRow);
}



public static void generateNavigationMapForFeatureSideLevel1(String parentfeature, String coreasset, String filter,
		String variant, String component) {
	
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
		"			                text: { name: 'diff ("+parentfeature+", "+productPortfolioToken+")' },"+"\n"+
		"							 HTMLclass: 'orange',"+"\n"+
				"			               children: [{"+"\n"+
						                	 " text: { name: 'diff ("+featureToken+" "+componentToken+", "+productPortfolioToken+")' },"+"\n"+
						                "	 HTMLclass: 'white',"+"\n"+
						                	 "children: [{"+"\n"+
							                	 "text: { name: 'diff ("+coreAssetsToken+", "+productPortfolioToken+")' },"+"\n"+
							                	 "children: [{"+"\n"+
					 "								text: { name: 'diff ("+coreAssetsToken+", "+productAssetToken+") ["+expressionToken+"]' },"+"\n"+
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



}
