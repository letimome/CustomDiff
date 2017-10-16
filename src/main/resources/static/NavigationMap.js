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
					HTMLclass: 'visitedBlue',
			        children: [
			            {
			                text: { name: 'diff (Feature, Product Portfolio)' },
			                children: [{
			                	 text: { name: 'diff (PL asset, Variant asset) [Filter]' },
			                } ]
			            },
						{
			                text: { name: 'diff(Feature, Product Variant)' },
			               		children: [{
			               	 		text: { name: 'diff (PL asset, Variant asset) [Filter]' },
			               		}]
			        		},
			            {
			                text: { name: 'diff(Platform, productSeville-v1.0)' },
			        			HTMLclass: 'orange',
			               		children: [{
			               	 		text: { name: 'diff (PL asset, Variant asset) [Filter]' },
			               		}]
			            }
			        ]
			    }
			};