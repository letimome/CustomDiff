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
			        text: { name: 'diff(Platform, Product Portfolio)' },
			      //  image: 'images/mini-alluvial.png',
					HTMLclass: 'visitedBlue',
			        children: [
			            {
			                text: { name: 'diff (AirPressure, Product Portfolio)' },
HTMLclass: 'orange',
children: [{
text: { },
			                children: [{
			                	 text: { name: 'diff (PL.PL asset, product.PL asset)[Filter]' },
								HTMLclass: 'white',
			                } ]
							} ]
			            },
			            {
			                text: { name: 'diff(Platform,Product Variant)' },
			          //      image: 'images/mini-product-treemap.png',
			                children: [{
			               	 	text: { name: 'diff (PL asset, Product Variant)' },
			            //   	 	image: 'images/mini-VP-Treemap.png',
			               		children: [{
			               	 		text: { name: 'diff (PL asset, Variant asset)[Filter]' },
			               		}]
			                } ]
			            }
			        ]
			    }
			};