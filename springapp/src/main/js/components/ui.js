import React from 'react';
import StartForm from './start-form'
import StatsView from './stats-view'
import Paper from 'material-ui/Paper';

const paper = {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    marginTop: "15px",
    width: "90%",
};

const center = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
};

class UI extends React.Component {
    constructor() {
        super();

        this.state = {
            isTableLoaded: false,
            table: null
        }
    }

    renderTable = () => {
        if (this.state.isTableLoaded) {
            return (
                <Paper zDepth={4} style={paper} >
                    <StatsView name={this.state.name} posts={this.state.posts} table={this.state.table}/>
                </Paper>
            );
        }
    }

    render() {
        return (
            <div style={center}>
                <StartForm handleSubmit={this.handleSubmit.bind(this)}/>
                {this.renderTable()}
            </div>
        );
    }

    handleSubmit = (table) => {
        this.setState({
            isTableLoaded: true,
            table: table,
        });
    };
}

export default UI;