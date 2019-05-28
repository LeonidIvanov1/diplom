import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Button, Col, Form, FormControl, InputGroup, Table} from "react-bootstrap";


class ReserveRequest extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            groups: "",
            group: "",
            discipline: "",
            disciplines: "",
            literatureCollection: [],
            searchLiterature: [],
            searchValue: "",
            error: false,
            selectDisciplines: ""
        }
        ;
    }


    componentDidMount() {
        this.getTeacherGroups();
        this.getTeacherDisciplines();
    }

    getTeacherGroups() {
        let data = {
            command: 'GET_TEACHER_GROUPS',
            user_id: window.sessionStorage.getItem('user_id')
        };

        let requestData = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: requestData
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({
                groups: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getTeacherDisciplines() {
        let data = {
            command: 'GET_TEACHER_DISCIPLINES',
            user_id: window.sessionStorage.getItem('user_id')
        };

        let requestData = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: requestData
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({
                disciplines: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
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

    getGroupOptions() {
        let rows = [];
        let specialties = this.state.groups;
        for (let i = 0; i < specialties.length; i++) {
            let row = <option value={specialties[i].groupID}>{specialties[i].name}</option>
            rows.push(row);
        }
        return rows;
    }

    getDisciplineOptions() {
        let rows = [];
        let specialties = this.state.selectDisciplines;
        for (let i = 0; i < specialties.length; i++) {
            let row = <option value={specialties[i]}>{specialties[i]}</option>
            rows.push(row);
        }
        return rows;
    }

    onCollectionSearchClick = () => {
        let data = {
            command: 'GET_book_collections',
            search_data: this.state.searchValue,
            start: 0,
            end: 5
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
            this.setState({searchLiterature: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    setBookType(data) {
        switch (data) {
            case 'BOOK':
                return 'Учебное пособие';
            case 'TRAINING':
                return 'Методическое пособие'
        }
    }


    getSearchLiterature() {
        let book_collection = this.state.searchLiterature;
        let rows = [];
        if (book_collection != null) {
            for (let i = 0; i < book_collection.length; i++) {
                let row = <tr key={i + book_collection[i].id} id={JSON.stringify(book_collection[i])}>
                    <td>{i + 1}</td>
                    <td>{book_collection[i].name}</td>
                    <td>{book_collection[i].author}</td>
                    <td>{book_collection[i].year}</td>
                    <td>{this.setBookType(book_collection[i].literatureType)}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    }

    getSelectedLiterature() {
        let book_collection = this.state.literatureCollection;
        let rows = [];
        if (book_collection != null) {
            for (let i = 0; i < book_collection.length; i++) {
                let row = <tr key={i + book_collection[i].id} id={JSON.stringify(book_collection[i])}>
                    <td>{i + 1}</td>
                    <td>{book_collection[i].name}</td>
                    <td>{book_collection[i].author}</td>
                    <td>{book_collection[i].year}</td>
                    <td>{this.setBookType(book_collection[i].literatureType)}</td>
                    <td><Button value={JSON.stringify(book_collection[i])} onClick={this.removeLiter}>Удалить</Button>
                    </td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    }

    removeLiter = (e) => {
        let id = JSON.parse(e.target.value);
        let selectedLiterature = this.state.literatureCollection;
        console.log(id);
        selectedLiterature.splice(selectedLiterature.indexOf(id), 1);
        this.setState({literatureCollection: selectedLiterature});
    };
    onSearchTableClick = (e) => {
        let id = JSON.parse(e.target.parentElement.getAttribute('id'));
        let selectedLiterature = this.state.literatureCollection;
        selectedLiterature.push(id);


        var myData = selectedLiterature;

        selectedLiterature = Array.from(new Set(myData.map(JSON.stringify))).map(JSON.parse);

        this.setState({literatureCollection: selectedLiterature});
    };

    setError() {
        if (this.state.error) {
            return <Alert key={'d'} variant="danger">
                Количества доступной литературы не хватает для резервирования
            </Alert>
        }
    }

    setGroupDisciplines(id) {
        let groupName;
        let disciplines = [];

        for (let i = 0; i < this.state.groups.length; i++) {
            if (+id === +this.state.groups[i].groupID) {
                groupName = this.state.groups[i].name;
                break;
            }
        }


        for (let i = 0; i < this.state.disciplines.length; i++) {
            if (this.state.disciplines[i].group === groupName) {
                disciplines.push(this.state.disciplines[i].discipline);
            }
        }

        this.setState({selectDisciplines: disciplines});
    }

    reserve = () => {
        let group;
        for (let i = 0; i < this.state.groups.length; i++) {
            if (+this.state.groups[i].groupID === +this.state.group) {
                group = this.state.groups[i];
            }
        }

        let litCount;
        for (let i = 0; i < this.state.literatureCollection.length; i++) {
            litCount += this.state.literatureCollection[i].inStockCount;
        }

        if (group.students.length > litCount) {
            this.setState({error: true});
        } else {
            let data = {
                command: "CREATE_RESERVE_REQUEST",
                literatureCollection: JSON.stringify(this.state.literatureCollection),
                group_id: this.state.group,
                discipline: this.state.discipline,
                teacher: window.sessionStorage.getItem("user_id")
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
                this.setState({searchLiterature: data.value})
            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        }


    }

    render() {
        return (
            <Container fluid="true">
                <Form>
                    <Form.Row>
                        <Form.Group as={Col} controlId="specialties">
                            <Form.Label>Учебная группа</Form.Label>
                            <Form.Control as="select" value={this.state.group} onChange={(event) => {
                                if (event.target.value !== 'default') {
                                    this.setState({group: event.target.value});
                                    this.setGroupDisciplines(event.target.value);
                                }
                            }}>
                                <option value="default">Выберите специальность</option>
                                {this.getGroupOptions()}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group as={Col} controlId="specialties">
                            <Form.Label>Дисциплина</Form.Label>
                            <Form.Control as="select" value={this.state.discipline} onChange={(event) => {
                                if (event.target.value !== 'default') {
                                    this.setState({discipline: event.target.value})
                                }
                            }}>
                                <option value="default">Выберите дисциплину</option>
                                {this.getDisciplineOptions()}
                            </Form.Control>
                        </Form.Group>
                    </Form.Row>

                    <p>Выбранные экземпляры для резервирования</p>
                    <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                        <tr>
                            <th>#</th>
                            <th>Название</th>
                            <th>Автор</th>
                            <th>Год</th>
                            <th>Тип</th>
                            <th>Действие</th>
                        </tr>

                        <tbody>
                        {this.getSelectedLiterature()}
                        </tbody>
                    </Table>
                    <p>Поиск</p>
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
                            <Button variant="primary" onClick={this.onCollectionSearchClick}>Поиск по
                                коллекциям</Button>
                        </InputGroup.Append>
                    </InputGroup>
                    <Table striped bordered hover className="data-table" onClick={this.onSearchTableClick}>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Название</th>
                            <th>Автор</th>
                            <th>Год</th>
                            <th>Тип</th>

                        </tr>
                        </thead>
                        <tbody>
                        {this.getSearchLiterature()}
                        </tbody>
                    </Table>
                    <Button onClick={this.reserve}>Зарезервировать</Button>
                </Form>
                {this.setError()}
            </Container>
        );
    }
}

export default ReserveRequest;
