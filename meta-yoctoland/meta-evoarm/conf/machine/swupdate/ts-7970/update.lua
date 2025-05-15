require ("swupdate")

function postinst()
   	local update_dev = io.open("/tmp/swupdate_update_dev", "rb"):read("*all")
	-- e2fsck -fy ${UPDATE_DEV} || true
	-- resize2fs ${UPDATE_DEV}
    local update_rootfs = swupdate.mount(update_dev, "ext4")

    if update_rootfs == nil then
	    return false, "Failed to mount update rootfs"
    end

	-- Copy current machine id to prevent it from changing
	swupdate.info("Backing up machine id")
    local dbus_dir = update_rootfs .. "/var/lib/dbus"
    os.execute("mkdir -p" .. dbus_dir)
    os.execute("cp /etc/machine-id " .. dbus_dir)
    os.execute("cp /etc/machine-id " .. update_rootfs .. "/etc/machine-id")

    --Copy non-zero sized ssh host key files
    os.execute("find /etc/ssh -name \"*key*\" -size +50c  -exec cp {} " .. update_rootfs .. "/etc/ssh \\; ")

    swupdate.info("rsyncing /usr/share/persistent-files")
    -- This is overly complicated because the existing image does not have rsync
    os.execute("LD_LIBRARY_PATH=\"" .. update_rootfs .. "/usr/lib:$LD_LIBRARY_PATH\" " .. update_rootfs .. "/usr/bin/rsync --archive --ignore-existing " .. update_rootfs .. "/usr/share/persistent-files/ /data")

	swupdate.info("Unmounting partitions")
	swupdate.umount(update_rootfs)

	return true, "Postinst succesful"
end
