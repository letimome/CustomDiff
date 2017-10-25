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
			        HTMLclass: 'orange',
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
			                text: { name: 'diff(Platform, Product Variant)' },
			               		children: [{
			               	 		text: { name: 'diff (PL asset, Variant asset) [Filter]' },
			               		}]
			            }
			        ]
			    }
			};