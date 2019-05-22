import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Button, Col, FormControl, InputGroup, Row, Table} from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import {LinkContainer} from "react-router-bootstrap";


class Users extends Component {

    perPage = 12;
    url = 'http://localhost:8088/library_war/FrontController?';
    inputData = null;
    curentPage = 0;

    constructor(props) {

        super(props);
        this.state = {users: null, pageCount: 5, searchValue: ""};
    }


    componentWillMount() {

    }

    componentDidMount() {
        this.getUserCount();
        this.getUsers();
    }



    getUsers() {
        let data = {
            command: 'GET_USERS',
            start: 0,
            end: this.perPage,
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
            this.setState({users: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getUserCount() {
        let data = {
            command: 'GET_USER_COUNT',
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
            this.setState({pageCount: Math.ceil(data.value / this.perPage)})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });

    }


    getRole(role) {
        switch (role) {
            case 'ADMIN':
                return "Администратор";
            case 'LIBRARIAN':
                return "Библиотекарь";
            case 'TEACHER':
                return "Преподаватель";
            case 'STUDENT':
                return "Студент";
            default:
                return "Неопределена";
        }
    }

    createUsers() {
        let users = this.state.users;
        let rows = [];
        if (users != null) {
            for (let i = 0; i < users.length; i++) {
                let row = <tr key={users[i].id + i} id={users[i].id}>
                    <td>{this.curentPage * this.perPage + i + 1}</td>
                    <td>{users[i].fullName}</td>
                    <td>{users[i].login}</td>
                    <td>{users[i].password}</td>
                    <td>{this.getRole(users[i].role)}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
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

    onSearchClick = event => {
        this.getUserCount();
        this.getUsers();
    };


    handlePageClick = data => {
        let selected = data.selected;
        this.curentPage = selected;
        let offset = Math.ceil(selected * this.perPage);
        let reqData = {
            command: 'GET_USERS',
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
            this.setState({users: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });


    };

    handleChange = data => {
        this.setState({searchValue: data.target.value});
    };

    onTableClick = (e) => {
        let id = e.target.parentElement.getAttribute('id');
        this.props.history.push("/user_info/" +id);
    };

    render() {

        return (

            <Container fluid="true">
                <Row>
                    <Col>
                        <LinkContainer to="/add_user">
                            <Button variant="primary">Добавить нового пользователя</Button>
                        </LinkContainer>
                    </Col>
                    <Col>
                        <InputGroup className="mb-3">
                            <FormControl
                                placeholder="Введите данные для поиска"
                                aria-label="Введите данные для поиска"
                                aria-describedby="basic-addon2"
                                value={this.state.searchValue}
                                onChange={this.handleChange}

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
                            <th>ФИО</th>
                            <th>Логин</th>
                            <th>Пароль</th>
                            <th>Должность</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.createUsers()}
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

export default Users;
