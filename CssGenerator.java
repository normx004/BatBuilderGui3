package batboy;

public class CssGenerator {
    String cssString = null;
	public CssGenerator(int count) {
		// TODO Auto-generated constructor stub
		if (count == 4) {
			cssString = new String("	<style> 	div.floatL {  float: left;  }\n  div.floatR {  float: right;  }  \n.columnRight {	width: 48%;	float: right;	padding: 10px;	margin-bottom: 20px;	}\n.columnLeft {	width: 48%;	float: left;	padding: 10px;	margin-bottom: 20px;}\n.columnMain {	width: 2%;	float: left;	padding: 10px;	margin-bottom: 20px;}\nh2 {  text-align: center;  color: pink;  background: grey;}  </style>\n  <h2>Very Hot Video</h2>");
		}
	}
	public String getCss(String s){
		return s + cssString;
	}

}
