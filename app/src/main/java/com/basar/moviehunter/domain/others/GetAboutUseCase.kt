package com.basar.moviehunter.domain.others

import com.basar.moviehunter.R
import com.basar.moviehunter.domain.uimodel.AboutUI
import com.basar.moviehunter.domain.uimodel.ConnectionButtonModel
import com.basar.moviehunter.domain.uimodel.TechnologyModel
import com.basar.moviehunter.extension.getDocumentLink
import com.basar.moviehunter.util.ConstantsHelper.GITHUB_URL
import com.basar.moviehunter.util.ConstantsHelper.LINKEDIN_URL
import com.basar.moviehunter.util.ConstantsHelper.RESUME_URL
import com.basar.moviehunter.util.ConstantsHelper.WEBSITE_URL
import com.basar.moviehunter.util.ResProvider
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAboutUseCase @Inject constructor(
    val resProvider: ResProvider
) : UseCase<Unit, AboutUI>() {

    override fun execute(params: Unit): Flow<AboutUI> = flow {
        val aboutUI = AboutUI(
            toolbarTitle = "İ. Başar YARGICI",
            toolbarRes = R.drawable.about_me_photo,
            welcomeTitle = resProvider.getString(R.string.welcome),
            description = resProvider.getString(R.string.about_me),
            technologyListTitle = resProvider.getString(R.string.techs_enjoy),
            technologyList = arrayListOf(
                TechnologyModel(
                    R.drawable.im_android,
                    resProvider.getString(R.string.android),
                ),
                TechnologyModel(
                    R.drawable.im_kotlin,
                    resProvider.getString(R.string.kotlin),
                ),
                TechnologyModel(
                    R.drawable.im_java,
                    resProvider.getString(R.string.java),
                ),
                TechnologyModel(
                    R.drawable.im_spring,
                    resProvider.getString(R.string.spring_boot),
                )
            ),
            connectTitle = resProvider.getString(R.string.contact_with_me),
            connectActionList = arrayListOf(
                ConnectionButtonModel(
                    resProvider.getString(R.string.visit_my_linkedin_account),
                    LINKEDIN_URL
                ),
                ConnectionButtonModel(
                    resProvider.getString(R.string.visit_my_github_account),
                    GITHUB_URL
                ),
                ConnectionButtonModel(
                    resProvider.getString(R.string.visit_my_website),
                    WEBSITE_URL
                ),
                ConnectionButtonModel(
                    resProvider.getString(R.string.open_my_resume_in_mid_2022),
                    getDocumentLink(RESUME_URL)
                ),
            )
        )
        emit(aboutUI)
    }
}