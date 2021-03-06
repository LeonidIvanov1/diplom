import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Button, Col, FormControl, InputGroup, Row, Table} from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import {LinkContainer} from "react-router-bootstrap";


class Discipline extends Component {

    perPage = 12;
    url = 'http://localhost:8088/library_war/FrontController?';
    curPage = 0;

    constructor(props) {
        super(props);
        this.state = {
            disciplines: null,
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

        this.getDisciplines();

    }


    getDisciplines = () => {
        let data = {
            command: 'GET_DISCIPLINES_ID',
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
            this.setState({disciplines: data.value, pageCount: Math.ceil(data.value.length / this.perPage)})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    createGroupsRows = () => {
        let groups = this.state.disciplines;
        let rows = [];
        console.log(groups);
        if (groups != null) {
            for (let i = 0; i < groups.length; i++) {
                let row = <tr key={i + groups[i].id} id={groups[i].disciplineID}>
                    <td>{this.curPage * this.perPage + i + 1}</td>
                    <td>{groups[i].name}</td>
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
            command: 'GET_DISCIPLINES',
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
        this.props.history.push("/discipline_info/" + id);
    };

    onSearchClick = event => {
        this.getUserCount();
        this.getUsers();
    };

    render() {

        return (
            <Container fluid="true">
                <Row>
                    <Col>
                        <LinkContainer to="/add_discipline">
                            <Button variant="primary">Добавить новую дисциплину</Button>
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
                        </tr>
                        </thead>
                        <tbody>
                        {this.createGroupsRows()}
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

export default Discipline;
