import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Button, Col, FormControl, InputGroup, Row, Table} from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import {LinkContainer} from "react-router-bootstrap";


class Specialties extends Component {

    perPage = 12;
    url = 'http://localhost:8088/library_war/FrontController?';
    curPage = 0;

    constructor(props) {
        super(props);
        this.state = {
            specialties: null,
            searchValue: "",
            pageCount: 0
        }

    }

    getRequestData(data) {
        var formData = new FormData();
        for (var name in data) {
            formData.append(name, data[name]);
        }
        let searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
        return searchParams;
    }

    componentDidMount() {

        this.getspecialties();

    }


    getspecialties = () => {
        let data = {
            command: 'GET_specialties',
            search_data: this.state.searchValue
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({specialties: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    createspecialtiesRows = () => {
        let specialties = this.state.specialties;
        let rows = [];
        if (specialties != null) {
            for (let i = 0; i < this.state.specialties.length; i++) {
                let row = <tr key={i + specialties[i].specialtyID} id={specialties[i].specialtyID}>
                    <td>{this.curPage * this.perPage + i + 1}</td>
                    <td>{specialties[i].name}</td>
                    <td>{specialties[i].specialtyCode}</td>
                    <td>{specialties[i].standardParameter}</td>
                    <td>{specialties[i].description}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    };

    handlePageClick = data => {
        let selected = data.selected;
        this.curentPage = selected;
        let offset = Math.ceil(selected * this.perPage);
        let reqData = {
            command: 'GET_specialties',
            start: offset,
            end: offset + this.perPage,
            search_data: this.state.searchValue
        };
        let req = this.getRequestData(reqData);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({specialties: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });


    };

    onTableClick = (e) => {
        let id = e.target.parentElement.getAttribute('id');
        console.log(id);
        this.props.history.push("/specialty_info/" + id);
    };

    onSearchClick = event => {

    };

    render() {

        return (
            <Container fluid="true">
                <Row>
                    <Col>
                        <LinkContainer to="/add_specialty">
                            <Button variant="primary">Добавить новую специальность</Button>
                        </LinkContainer>
                    </Col>
                    <Col>
                        <InputGroup className="mb-3">
                            <FormControl
                                placeholder="Введите данные для поиска"
                                aria-label="Введите данные для поиска"
                                aria-describedby="basic-addon2"
                                value={this.state.searchValue}
                                onChange={(e) => {
                                    this.setState({
                                            searchValue: e.target.value
                                        }
                                    )
                                    ;
                                }}

                            />
                            <InputGroup.Append>
                                <Button variant="primary" onClick={this.onSearchClick}>Поиск</Button>
                            </InputGroup.Append>
                        </InputGroup>
                    </Col>
                </Row>
                <Row>
                    <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Название</th>
                            <th>Код</th>
                            <th>Параметр ФГОС</th>
                            <th>Описание</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.createspecialtiesRows()}
                        </tbody>
                    </Table>
                </Row>
                <Row>
                    <Col md="auto" className="paging">
                        <ReactPaginate
                            previousLabel={'Предыдущая'}
                            nextLabel={'Следующая'}
                            breakLabel={'...'}
                            breakClassName={'break-me'}
                            pageCount={this.state.pageCount}
                            marginPagesDisplayed={2}
                            pageRangeDisplayed={5}
                            onPageChange={this.handlePageClick}
                            containerClassName={'pagination'}
                            subContainerClassName={'pages pagination'}
                            activeClassName={'active'}
                        />
                    </Col>
                </Row>
            </Container>

        );
    }
}

export default Specialties;
