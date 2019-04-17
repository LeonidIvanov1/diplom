import React, {Component} from 'react';
import {Navbar} from "react-bootstrap";

class Footer extends Component {
    render() {
        return (
            <Navbar bg="light" variant="light" fixed="bottom">

                <Navbar.Toggle/>
                <Navbar.Collapse className="justify-content-end">
                    <Navbar.Text>
                        © 2019 РГРТУ Иванов Л.А.
                    </Navbar.Text>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default Footer;
