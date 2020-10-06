import React from 'react';
import {Link, withRouter} from "react-router-dom";
import {Form, Col, Row, Button, Jumbotron, Container} from 'react-bootstrap';
import '../App.css';

  class About extends React.Component {
    constructor(props){
        super(props)
        this.state = {
        };
    }
    componentDidMount(){
      
    }

render() {
 
return(
  <div className= "page">
  <Container fluid >
  <Row >
    <Col md = {{span: 6, offset:3}} sm = {{span:12}}>
        <h1>Programación funcional</h1>
    </Col>
    <Col md = {{span: 6, offset:3}} sm = {{span:12}}>
        <h3>Alumno: Walter Perez</h3>
    </Col>
  </Row>
  
  </Container>
  </div>
);
}
}

export default  withRouter (About);