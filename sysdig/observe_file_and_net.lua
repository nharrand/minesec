-- Chisel description
description = "Logs file access and network access";
short_description = "Logs file access and network access";
category = "I/O";

-- Argument list
args = {}

-- Imports and globals
require "common"
local verbose = false

-- Argument notification callback
function on_set_arg(name, val)
	return false
end

-- Initialization callback
function on_init()	
	local filter

	-- Request the fields that we need
	fbuf = chisel.request_field("evt.buffer")
	fdata = chisel.request_field("evt.arg.data")
	ffdname = chisel.request_field("fd.name")
	fisw = chisel.request_field("evt.is_io_write")
	fpid = chisel.request_field("proc.pid")
	fpname = chisel.request_field("proc.name")
	fres = chisel.request_field("evt.rawarg.res")
	ftid = chisel.request_field("thread.tid")
	fts = chisel.request_field("evt.time")
	
	frip = chisel.request_field("fd.rip")
	ftype = chisel.request_field("fd.types")

	-- increase the snaplen so we capture more of the conversation 
	sysdig.set_snaplen(2000)

	-- set the output format to ascii
	sysdig.set_output_format("ascii")

	-- set the filter
	filter = "((not fd.name contains /dev/pt and not fd.name contains /dev/tty) and "

	filter = string.format("%s%s", filter, "evt.is_io=true and ")

	filter = string.format("%s%s", filter, "fd.type=file and evt.dir=< and evt.failed=false) or ")
	
	filter = string.format("%s%s", filter, "(evt.is_io=true and (fd.type=ipv4 or fd.type=ipv6))")

	if verbose then
		print("filter=" .. filter)
	end

	chisel.set_filter(filter)

	return true
end

-- Event parsing callback
function on_event()
	local ty = evt.field(ftype)
	if ty == "ipv4" or ty == "ipv6" then
		local rip = evt.field(frip)
		print(string.format("NET-ACCESS %s %s", ty, rip))
	else
		-- Extract the event details
		--local data = evt.field(fdata)
		local fdname = evt.field(ffdname)
		local is_write = evt.field(fisw)
		local pid = evt.field(fpid)
		local pname = evt.field(fpname)
		local res = evt.field(fres)
		local ts = evt.field(fts)
		local read_write

		-- Render the message to screen
		if is_write == true then
			read_write = "W"
		else
			read_write = "R"
		end

		--print(string.format("FS-ACCESS %s %s(%s) %s %s %s %s", ts, pname, pid, read_write, format_bytes(res), fdname, data))
		print(string.format("FS-ACCESS %s , %s(%s) , %s , %s , %s", ts, pname, pid, read_write, fdname, data))
	end
	
	return true
end

