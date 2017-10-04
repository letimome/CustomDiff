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
					HTMLclass: 'visitedBlue',
			        children: [
			            {
			                text: { name: 'diff (feature, product-portfolio)' },
			              //  image: 'images/mini-feature-treemap.png',
			                children: [{
			                	 text: { name: 'diff (core-asset, product)[Expression]' },
			        //        	 image: 'images/mini-diff.png'
			                } ]
			            },
			            {
			                text: { name: 'diff(features,productBerlin-v2.0)' },
			          //      image: 'images/mini-product-treemap.png',
 								HTMLclass: 'orange',
			                children: [{
			               	 	text: { name: 'diff (core-asset, productBerlin-v2.0)' },
			            //   	 	image: 'images/mini-VP-Treemap.png',
 								HTMLclass: 'white',
			               		children: [{
			               	 		text: { name: 'diff (core-asset, productBerlin-v2.0)[Expression]' },
 									HTMLclass: 'white',
			              // 		image: 'images/mini-diff.png'
			               		}]
			                } ]
			            }
			        ]
			    }
			};