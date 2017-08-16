import React from 'react';
import StartForm from './start-form'
import StatsView from './stats-view'
import Paper from 'material-ui/Paper';
import Snackbar from 'material-ui/Snackbar';
import RaisedButton from 'material-ui/RaisedButton';

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
            open: false,
            isTableLoaded: false,
            table: null
        }
    }

    renderTable = () => {
        if (this.state.isTableLoaded) {
            return (
                <Paper zDepth={4} style={paper} >
                    <RaisedButton onClick={this.handleTouchTap}
                                  label="Add to database"/>

                    <StatsView table={this.state.table}/>

                    <Snackbar contentStyle={center}
                              open={this.state.open}
                              message="Stats saved"
                              autoHideDuration={4000}
                              onRequestClose={this.handleRequestClose}
                    />
                </Paper>
            );
        }
    };

    handleTouchTap = () => {
        this.setState({
            open: true,
        });
    };

    handleRequestClose = () => {
        this.setState({
            open: false,
        });
    };

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