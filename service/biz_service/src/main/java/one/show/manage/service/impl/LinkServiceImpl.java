package one.show.manage.service.impl;

import one.show.manage.dao.LinkMapper;
import one.show.manage.domain.Link;
import one.show.manage.service.LinkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Haliaeetus leucocephalus on 18/1/19.
 */
@Component("linkService")
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkMapper linkMapper;
    @Override
    public List<Link> findLinkList() {
        return linkMapper.findLinkList();
    }
}
