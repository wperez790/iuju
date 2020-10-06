import React from 'react';
import {Navbar, Nav, Button} from 'react-bootstrap';
import {Link} from 'react-router-dom'
import { BsFillHouseDoorFill } from "react-icons/bs";
import '../App.css';



//const Navbar = () => (

class NavBar extends React.Component {
    constructor(props){
    super(props);
    this.state = {date: new Date()};
    }
    
render() {
    return(
        <Navbar  collapseOnSelect expand="lg" bg="dark" variant="dark">
          <Button href="http://localhost:3000/home" variant="primary">
            <BsFillHouseDoorFill />
          </Button>
        <Navbar.Brand href="http://localhost:3000/home">iuju !</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          <Link to="/home" className="navbar-text with-side-margin">Home</Link>
          <Link to="/home" className="navbar-text with-side-margin">Tasks</Link>
          <Link to="/home" className="navbar-text with-side-margin">Events</Link>
          <Link to="/notes" className="navbar-text with-side-margin">Notes</Link>
          <Link to="/recorder" className="navbar-text with-side-margin">Recorder</Link>
          <Link to="/about" className="navbar-text with-side-margin">About</Link>
        </Nav>
        </Navbar.Collapse>
      </Navbar>
    
    
      
);

}
}

export default NavBar;