package val.bond.applogic.FileSystem;

import val.bond.applogic.NetStuff.Net.TransferPackage;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

interface ICommand{
    void start(Command command, TransferPackage transferPackage, Stream data) throws UnsupportedEncodingException;
}
