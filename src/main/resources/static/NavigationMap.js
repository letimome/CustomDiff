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
			        text: { name: 'diff(PL Features, Product Variants)' },
			        HTMLclass: 'orange',
			        children: [
			            {
			                text: { name: 'diff (PL Feature, Product Variants)' },
							children: [{
			               	   text: { },
			                children: [{
			                	 text: { name: 'diff (PL asset, Variant asset)[Cond. Expression]' },
			                } ]
			                } ]
			            },
			            {
			                text: { name: 'diff(PL Features,Product Variant)' },
			                children: [{
			               	 	text: { name: 'diff (PL asset, Product Variant)' },
			               		children: [{
			               	 		text: { name: 'diff (PL asset, Variant asset)[Cond. Expression]' },
			               		}]
			                } ]
			            }
			        ]
			    }
			};