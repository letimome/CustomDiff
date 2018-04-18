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
			        text: { name: 'diff(Parent features, Product Portfolio)' },
			        HTMLclass: 'orange',
			        children: [
			            {
			                text: { name: 'diff (Child features, Product Portfolio)' },
			               children: [{
 text: { name: 'diff (Components, Product Portfolio)' },
children: [{
text: { name: 'diff (PL assets, Product Portfolio)' },
children: [{
								text: { name: 'diff (PL assets, Product Variant) [Filter]' },
			                } ]
										} ]
										} ]
			            },
						{
			                text: { name: 'diff(Child features, Product Variant Components)' },
							children: [{
 								text: { name: 'diff (Component, Product Variant)' },
								children: [{
											text: { name: 'diff (PL asset, variant asset) [Filter]' },
			               				 } ]
									} ]
			        		},
			            {
			                text: { name: 'diff(Parent features, Product Variant Components)' },
							children: [{
 								text: { name: 'diff (Parent features, Component variant asset)' },
									children: [{
											text: { name: 'diff (PL asset, variant asset) [Filter]' },
			               					 } ]
									} ]
			            }
			        ]
			    }
			};