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
			        HTMLclass: 'orange',
			      //  image: 'images/mini-alluvial.png',
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