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
			        HTMLclass: 'orange',
			        children: [
			            {
			                text: { name: 'diff (Features, Product Portfolio)' },
children: [{
								text: { name: 'diff (PL assets, Product Variant) [Filter]' },
			                } ]},
						{
			                text: { name: 'diff(Feature, Product Variant)' },
							children: [{
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