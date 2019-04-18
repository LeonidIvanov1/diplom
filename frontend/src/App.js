import React, {Component} from 'react';
import './App.css';
import Header from "./components/Header";
import Footer from "./components/Footer";
import Content from "./components/Content";
import Container from "react-bootstrap/Container";
import {Row} from "react-bootstrap";
import Col from "react-bootstrap/Col";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {page: null};
    }

    onPageChange = (page) => {
        this.setState({page: page});
    };

    render() {
        return (
            <Container fluid={true}>
                <Row>
                    <Header pageChange={this.onPageChange}/>
                </Row>
                <Row>
                    <Col>
                        <Content page={this.state.page}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Footer/>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default App;
