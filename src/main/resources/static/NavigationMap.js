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
			        text: { name: 'diff(Features, Product Portfolio)' },
			        HTMLclass: 'blue',
			        children: [
			            {
			                text: { name: 'diff (Feature, Product Portfolio)' },
			               children: [{
								text: { name: 'diff (PL asset, variant asset) [Filter]' },
									} ]
			            },
						{
			                text: { name: 'diff(Feature, Product Variant)' },
			 	  	       HTMLclass: 'blue',
							children: [{
			       				 HTMLclass: 'orange',
											text: { name: 'diff (PL asset, variant asset) [Filter]' },
									} ]
			        		},
			            {
			                text: { name: 'diff(Features, Product Variant)' },
							children: [{
											text: { name: 'diff (PL asset, variant asset) [Filter]' },
			               					 } ]
			            }
			        ]
			    }
			};