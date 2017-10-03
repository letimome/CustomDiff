	var simple_chart_config = { 
  chart: { 
   container: '#tree-simple', 
		        	connectors: {
			                type: 'step' 
			            },
			            node: {
			                HTMLclass: 'nodeExample1'
			            }
			    },    
			    nodeStructure: {
			        text: { name: 'diff(features, product-portfolio)' },
			      //  image: 'images/mini-alluvial.png',
			        children: [
			            {
			                text: { name: 'diff (AirPressure, product-portfolio)' },
			              //  image: 'images/mini-feature-treemap.png',
HTMLclass: 'blue',
			                children: [{
			                	 text: { name: 'diff (settings.js, productBerline-v1.0)[hasFeature(AirPressure)]' },
			        //        	 image: 'images/mini-diff.png'
								HTMLclass: 'blue',
			                } ]
			            },
			            {
			                text: { name: 'diff(features,product)' },
			          //      image: 'images/mini-product-treemap.png',
			                children: [{
			               	 	text: { name: 'diff (core-asset, product)' },
			            //   	 	image: 'images/mini-VP-Treemap.png',
			               		children: [{
			               	 		text: { name: 'diff (core-asset, product)[Expression]' },
			              // 			image: 'images/mini-diff.png'
			               		}]
			                } ]
			            }
			        ]
			    }
			};